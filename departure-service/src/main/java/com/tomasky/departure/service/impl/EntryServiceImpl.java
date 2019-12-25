package com.tomasky.departure.service.impl;

import com.alibaba.fastjson.JSON;
import com.tomasky.departure.bo.DelayEntryBo;
import com.tomasky.departure.common.utils.BaseModelUtils;
import com.tomasky.departure.common.utils.CommonUtils;
import com.tomasky.departure.common.utils.DateUtils;
import com.tomasky.departure.cons.Constants;
import com.tomasky.departure.enums.DepartureAuditStatusEnum;
import com.tomasky.departure.enums.EmployeeJobStatus;
import com.tomasky.departure.helper.EntryHelper;
import com.tomasky.departure.helper.GuideHelper;
import com.tomasky.departure.mapper.CompanyInfoMapper;
import com.tomasky.departure.mapper.DepartureInfoMapper;
import com.tomasky.departure.mapper.UserRoleInfoMapper;
import com.tomasky.departure.model.CompanyInfo;
import com.tomasky.departure.model.DepartureInfo;
import com.tomasky.departure.model.UserRoleInfo;
import com.tomasky.departure.service.EntryService;
import com.tomasky.departure.vo.DelayEntryVo;
import com.tomasky.departure.vo.EmployeeCheckVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by sam on 2019-08-16.15:10
 */
@Service
public class EntryServiceImpl implements EntryService {

    private static Logger logger = LoggerFactory.getLogger(EntryServiceImpl.class);

    @Resource
    private UserRoleInfoMapper userRoleInfoMapper;
    @Resource
    private DepartureInfoMapper departureInfoMapper;
    @Resource
    private CompanyInfoMapper companyInfoMapper;
    @Resource
    private GuideHelper guideHelper;
    @Resource
    private EntryHelper entryHelper;

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public void delayEntry(DelayEntryBo delayEntryBo) {
        logger.info("预备入职接口入参：" + JSON.toJSONString(delayEntryBo));
        if(delayEntryBo.isGuideMode()) {
            return;
        }
        checkDelayEntryBo(delayEntryBo);
        Integer departureId = delayEntryBo.getDepartureId();
        Integer companyId = delayEntryBo.getCompanyId();
        // 离职表单对象
        DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(departureId);
        if (departureInfo == null) {
            throw new RuntimeException("离职表单不存在");
        }
        String auditStatus = departureInfo.getAuditStatus();
        if(! DepartureAuditStatusEnum.FINISH.getValue().equals(auditStatus)) {
            throw new RuntimeException("离职表单状态不是已办结");
        }
        Integer userId = delayEntryBo.getUserId();
        departureInfo.setFollowUserId(userId);
        // 修改离职表单状态为待入职
        departureInfo.setAuditStatus(DepartureAuditStatusEnum.DELAY_ENTRY.getValue());
        departureInfo.setNextCompanyId(companyId);
        departureInfo.setDelayEntryDate(new Date());
        new BaseModelUtils<>().buildModifiyEntity(departureInfo, userId);
        departureInfoMapper.updateByPrimaryKeySelective(departureInfo);
        logger.info("预备入职流程完成");
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public void join(DelayEntryBo delayEntryBo) {
        logger.info("入职接口入参：" + JSON.toJSONString(delayEntryBo));
        if(delayEntryBo.isGuideMode()) {
            return;
        }
        checkDelayEntryBo(delayEntryBo);
        Integer departureId = delayEntryBo.getDepartureId();
        Integer userId = delayEntryBo.getUserId();
        // 离职表单对象
        DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(departureId);
        if (departureInfo == null) {
            throw new RuntimeException("离职表单不存在");
        }
        String auditStatus = departureInfo.getAuditStatus();
        if(! DepartureAuditStatusEnum.DELAY_ENTRY.getValue().equals(auditStatus) && ! DepartureAuditStatusEnum.FINISH.getValue().equals(auditStatus)) {
            throw new RuntimeException("离职表单状态不是已办结或预备入职状态");
        }
        departureInfo.setAuditStatus(DepartureAuditStatusEnum.ENTRY.getValue());
        Integer followUserId = departureInfo.getFollowUserId();
        if(followUserId == null) {
            departureInfo.setFollowUserId(userId);
        }
        // 设置入职时间
        departureInfo.setEntryDate(DateUtils.format(new Date()));
        new BaseModelUtils<>().buildModifiyEntity(departureInfo, userId);
        departureInfoMapper.updateByPrimaryKeySelective(departureInfo);
        Integer companyId = delayEntryBo.getCompanyId();
        CompanyInfo companyInfo = companyInfoMapper.selectByPrimaryKey(companyId);
        if (companyInfo == null) {
            throw new RuntimeException("公司不存在");
        }
        Integer incumbentsCount = companyInfo.getIncumbentsCount();
        companyInfo.setIncumbentsCount(incumbentsCount + 1);
        new BaseModelUtils<>().buildModifiyEntity(companyInfo, userId);
        companyInfoMapper.updateByPrimaryKeySelective(companyInfo);
        logger.info("入职流程完成");
    }

    @Override
    public Map<String, Object> findDelayEntry(Integer userId, Integer companyId, String type, String mode, String nickName) {
        logger.info("查询离职列表接口入参userId：" + userId + "，companyId：" + companyId + ",type=" + type + ",mode=" + mode + ",nickName=" + nickName);
        try {
            if(Constants.MODE_GUIDE.equals(mode)) {
                return guideHelper.getDelayEntryListData();
            }
            DepartureAuditStatusEnum departureAuditStatusEnum = null;
            if(Constants.DELAY_ENTRY.equals(type)) {
                departureAuditStatusEnum = DepartureAuditStatusEnum.DELAY_ENTRY;
            } else if(Constants.ENTRY.equals(type)) {
                departureAuditStatusEnum = DepartureAuditStatusEnum.ENTRY;
            } else {
                throw new RuntimeException("位置的状态类型");
            }
            UserRoleInfo userRoleInfo = userRoleInfoMapper.selectByUserIdAndCompanyId(userId, companyId);
            if (userRoleInfo == null) {
                throw new RuntimeException("用户不存在");
            }
            if(! EmployeeJobStatus.INCUMBENCY.getValue().equals(userRoleInfo.getJobStatus())) {
                throw new RuntimeException("当前用户在职状态异常");
            }
            List<DelayEntryVo> delayEntryVoList = departureInfoMapper.selectDelayEntryVoList(companyId, departureAuditStatusEnum.getValue(), nickName);
            if(! CollectionUtils.isEmpty(delayEntryVoList)) {
                for(DelayEntryVo delayEntryVo : delayEntryVoList) {
                    DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(delayEntryVo.getDepartureId());
                    boolean chatAble = entryHelper.isChatAble(departureInfo, userId);
                    delayEntryVo.setChatAble(chatAble);
                }
            }
            logger.info("查询离职列表接口结束");
            return CommonUtils.setSuccessInfo(delayEntryVoList);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
    }

    /**
     * 校验输入参数
     * @param delayEntryBo
     */
    private void checkDelayEntryBo(DelayEntryBo delayEntryBo) {
        Integer departureId = delayEntryBo.getDepartureId();
        if (departureId == null) {
            throw new RuntimeException("离职表单ID不能为空");
        }
        Integer companyId = delayEntryBo.getCompanyId();
        if (companyId == null) {
            throw new RuntimeException("公司ID不能为空");
        }
        if(departureId.equals(companyId)) {
            throw new RuntimeException("不能再次入职到原公司");
        }
        Integer userId = delayEntryBo.getUserId();
        if (userId == null) {
            throw new RuntimeException("用户ID不能为空");
        }
    }

    @Override
    public Map<String, Object> findEmployeeCheckList(Integer userId, Integer companyId, String type) {
        if(! Constants.CHECK_TYPE_SEND.equals(type) && ! Constants.CHECK_TYPE_JOIN.equals(type)) {
            throw new RuntimeException("未知的背调类型");
        }
        List<EmployeeCheckVo> employeeCheckVoList = departureInfoMapper.selectEmployeeCheckVoList(userId, companyId, type);
        return CommonUtils.setSuccessInfo(employeeCheckVoList);
    }
}