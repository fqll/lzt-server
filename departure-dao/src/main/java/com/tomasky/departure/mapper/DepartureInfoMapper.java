package com.tomasky.departure.mapper;

import com.tomasky.departure.model.DepartureInfo;
import com.tomasky.departure.vo.ApproverLogVo;
import com.tomasky.departure.vo.AuditDepartureVo;
import com.tomasky.departure.vo.DelayEntryVo;
import com.tomasky.departure.vo.DepartureInfoListVo;
import com.tomasky.departure.vo.DepartureRateVo;
import com.tomasky.departure.vo.DraftListVo;
import com.tomasky.departure.vo.EmployeeCheckVo;
import com.tomasky.departure.vo.MonthDepartureRateVo;
import com.tomasky.departure.vo.QuitEmployeeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by sam on 2019-08-05.11:16
 */

@Mapper
public interface DepartureInfoMapper {

    /**
     * 根据公司ID和统计离职的开始时间查询期间的离职人数
     *
     * @param companyId
     * @param beginDate
     * @return
     */
    int selectDepartureCount(@Param("companyId") Integer companyId, @Param("beginDate") String beginDate);

    /**
     * 查询个人离职原因饼状图数据
     *
     * @param companyId
     * @return
     */
    List<DepartureRateVo> selectDepartureRateVoList(@Param("companyId") Integer companyId);

    /**
     * 查询公司离职原因饼状图数据
     *
     * @param companyId
     * @return
     */
    List<DepartureRateVo> selectCompanyDepartureRateVoList(@Param("companyId") Integer companyId);

    /**
     * 根据用户ID和公司ID查询待审批离职表单列表
     *
     * @param userId
     * @param companyId
     * @return
     */
    List<AuditDepartureVo> selectAuditDepartureVoList(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 根据用户ID和公司ID查询我已审批离职表单列表
     *
     * @param userId
     * @param companyId
     * @return
     */
    List<AuditDepartureVo> selectAuditedDepartureVoList(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 根据用户ID和公司ID查询我创建的离职表单列表
     *
     * @param userId
     * @param companyId
     * @return
     */
    List<AuditDepartureVo> selectCreatedDepartureVoList(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 查询抄送列表
     *
     * @param userId
     * @param companyId
     * @return
     */
    List<AuditDepartureVo> selectCopyDepartureVoList(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 查询自己的离职证明
     *
     * @param userId
     * @param companyId
     * @return
     */
    List<AuditDepartureVo> selectSelfDepartureVoList(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 根据用户ID和核验状态查询离职列表
     *
     * @param userId
     * @return
     */
    List<DepartureInfoListVo> selectValidDepartureInfoList(@Param("userId") Integer userId);

    /**
     * 根据离职表单ID查询离职表单创建者相关信息的详情
     *
     * @param id
     * @return
     */
    ApproverLogVo selectCreateLog(Integer id);

    /**
     * 查询离职员工库
     *
     * @param companyId
     * @param companyId
     * @return
     */
    List<QuitEmployeeVo> selectQuitEmployeeVoList(@Param("companyId") Integer companyId, @Param("nickName") String nickName);

    /**
     * 根据公司ID，离职日期的开始结束时间查询月离职情况
     *
     * @param companyId
     * @param beginDate
     * @param endDate
     * @return
     */
    List<MonthDepartureRateVo> selectMonthDepartureRateVoList(@Param("companyId") Integer companyId, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    /**
     * 查询待入职列表
     *
     * @param companyId
     * @param auditStatus
     * @return
     */
    List<DelayEntryVo> selectDelayEntryVoList(@Param("companyId") Integer companyId, @Param("auditStatus") String auditStatus, @Param("nickName") String nickName);

    /**
     * 查询离职表单草稿箱列表
     *
     * @param userId
     * @param companyId
     * @return
     */
    List<DraftListVo> selectDraftLis(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 根据用户ID和公司ID查询待我审批未读消息数量
     *
     * @param userId
     * @param companyId
     * @return
     */
    int selectUnreadApprovalCount(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 根据用户ID和公司ID查询抄送我的未读消息数量
     *
     * @param userId
     * @param companyId
     * @return
     */
    int selectUnreadCopyCount(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 查询离职表单核验码的唯一性
     *
     * @param departureCode
     * @return
     */
    int selectDepartureCode(String departureCode);

    int deleteByPrimaryKey(Integer id);

    int insert(DepartureInfo record);

    int insertSelective(DepartureInfo record);

    DepartureInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DepartureInfo record);

    int updateByPrimaryKey(DepartureInfo record);

    /**
     * 根据背调类型，查询背调员工列表
     * @param userId
     * @param companyId
     * @param type 0:发起背调，1：配合背调
     * @return
     */
    List<EmployeeCheckVo> selectEmployeeCheckVoList(@Param("userId") Integer userId, @Param("companyId") Integer companyId, @Param("type") String type);
}