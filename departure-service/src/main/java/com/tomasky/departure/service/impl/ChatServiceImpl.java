package com.tomasky.departure.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.tomasky.departure.bo.ChatBo;
import com.tomasky.departure.common.utils.BaseModelUtils;
import com.tomasky.departure.cons.Constants;
import com.tomasky.departure.enums.CheckStageEnum;
import com.tomasky.departure.helper.GuideHelper;
import com.tomasky.departure.mapper.ChatLogMapper;
import com.tomasky.departure.mapper.CompanyInfoMapper;
import com.tomasky.departure.mapper.DepartureInfoMapper;
import com.tomasky.departure.mapper.UserInfoMapper;
import com.tomasky.departure.model.ChatLog;
import com.tomasky.departure.model.CompanyInfo;
import com.tomasky.departure.model.DepartureInfo;
import com.tomasky.departure.model.UserInfo;
import com.tomasky.departure.service.ChatService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by sam on 2019-08-19.15:38
 */
@Service
public class ChatServiceImpl implements ChatService {
    private static Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);
    @Resource
    private DepartureInfoMapper departureInfoMapper;
    @Resource
    private ChatLogMapper chatLogMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private CompanyInfoMapper companyInfoMapper;
    @Resource
    private GuideHelper guideHelper;

    @Override
    public Map<String, Object> findChatList(Integer userId, Integer companyId, Integer departureId, String mode) {
        logger.info("查询聊天记录列表接口输入参数：userId=" + userId + ",companyId=" + companyId +  "，departureId=" + departureId + ",mode=" + mode);
        if(Constants.MODE_GUIDE.equals(mode)) {
            return guideHelper.getChatListData();
        }
        // 校验离职表单
        DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(departureId);
        if (departureInfo == null) {
            throw new RuntimeException("离职表单对象不存在");
        }
        String chatTitle = "";
        Integer otherCompanyId = null;
        Integer departureInfoCompanyId = departureInfo.getCompanyId();
        if(companyId.equals(departureInfoCompanyId)) {
            otherCompanyId = departureInfo.getNextCompanyId();
        } else {
            otherCompanyId = departureInfoCompanyId;
        }
        if(otherCompanyId == null) {
            throw new RuntimeException("获取其他公司ID失败");
        }
        CompanyInfo companyInfo = companyInfoMapper.selectByPrimaryKey(otherCompanyId);
        if (companyInfo == null) {
            throw new RuntimeException("获取其他公司信息失败");
        }
        chatTitle = companyInfo.getCompanyName();
        UserInfo selfInfo = userInfoMapper.selectByPrimaryKey(userId);
        if (selfInfo == null) {
            throw new RuntimeException("用户不存在");
        }
        List<UserInfo> otherUserInfoList = null;
        List<Integer> userIdList = chatLogMapper.selectOtherUserIdListInChat(departureId, userId);
        if(! CollectionUtils.isEmpty(userIdList)) {
            otherUserInfoList = userInfoMapper.selectByUserIdList(userIdList);
        }
        // 查询聊天记录
        List<ChatLog> chatLogList = chatLogMapper.selectChatLogListByDepartureId(departureId);
        Map<String, Object> result = Maps.newHashMap();
        result.put("chatTitle", chatTitle);
        result.put("chatList", chatLogList);
        result.put("selfInfo", selfInfo);
        result.put("otherInfo", otherUserInfoList);
        logger.info("查询聊天记录列表接口返回:" + JSON.toJSONString(chatLogList));
        return result;
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public void sendMsg(ChatBo chatBo) {
        logger.info("聊天接口输入参数:" + JSON.toJSONString(chatBo));

        Integer departureId = chatBo.getDepartureId();
        if (departureId == null) {
            throw new RuntimeException("离职表单ID不能为空");
        }
        Integer userId = chatBo.getUserId();
        if (userId == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        DepartureInfo departureInfo = departureInfoMapper.selectByPrimaryKey(departureId);
        if (departureInfo == null) {
            throw new RuntimeException("离职表单对象不存在");
        }
        String checkStage = departureInfo.getCheckStage();
        if(StringUtils.isBlank(checkStage) || CheckStageEnum.NOT_BEGUN.getValue().equals(checkStage)) {
            int listSize = chatLogMapper.selectChatLogListSize(departureId);
            if(listSize == 0) {
                // 第一次发起聊天时，修改背调状态为：开始聊天背调
                departureInfo.setCheckStage(CheckStageEnum.IN_CHAT.getValue());
                new BaseModelUtils<>().buildModifiyEntity(departureInfo, userId);
                departureInfoMapper.updateByPrimaryKeySelective(departureInfo);
            }
        }
        ChatLog chatLog = new ChatLog(departureId, userId, chatBo.getChatContent());
        new BaseModelUtils<>().buildCreateEntity(chatLog, userId);
        chatLogMapper.insert(chatLog);
        logger.info("聊天接口执行结束");
    }

}
