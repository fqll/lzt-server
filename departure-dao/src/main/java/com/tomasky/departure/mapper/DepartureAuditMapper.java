package com.tomasky.departure.mapper;

import com.tomasky.departure.model.DepartureAudit;
import com.tomasky.departure.vo.ApproverLogVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by sam on 2019-08-05.11:16
 */
@Mapper
public interface DepartureAuditMapper {

    /**
     * 根据离职表单ID查询审批人列表
     *
     * @param departureId
     * @return
     */
    List<ApproverLogVo> selectDepartureAuditList(@Param("departureId") Integer departureId);

    /**
     * 根据离职表单ID查询抄送人列表
     *
     * @param departureId
     * @return
     */
    List<ApproverLogVo> selectDepartureCopyList(@Param("departureId") Integer departureId);

    /**
     * 根据离职表单ID，用户ID和审批角色查询离职审批详情
     * @param departureId
     * @param userId
     * @param auditRoleType
     * @return
     */
    DepartureAudit selectCurrentAudit(@Param("departureId") Integer departureId, @Param("userId") Integer userId, @Param("auditRoleType") String auditRoleType);

    /**
     * 查询关注人数
     * @param departureId
     * @param auditRoleType
     * @return
     */
    int selectFollowedCount(@Param("departureId") Integer departureId, @Param("auditRoleType") String auditRoleType);

    /**
     * 查询下一轮审批人
     *
     * @param departureId
     * @param auditOrder
     * @return
     */
    DepartureAudit selectNextAudit(@Param("departureId") Integer departureId, @Param("auditOrder") Integer auditOrder);

    /**
     * 查询离职表单中已经审批过的审批记录
     *
     * @param departureId
     * @return
     */
    List<DepartureAudit> selectAuditedDepartureList(Integer departureId);

    /**
     * 初始化第一顺位人审批信息
     * @param departureId
     * @return
     */
    int initFirstAuditedDeparture(Integer departureId);

    /**
     * 根据离职表单ID删除全部审批内容
     * @param departureId
     */
    void deleteDepartureAuditList(Integer departureId);

    /**
     * 初始化其他顺位审批人信息
     * @param departureId
     * @return
     */
    int initOtherAuditedDeparture(Integer departureId);

    int deleteByPrimaryKey(Integer id);

    int insert(DepartureAudit record);

    int insertSelective(DepartureAudit record);

    DepartureAudit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DepartureAudit record);

    int updateByPrimaryKey(DepartureAudit record);
}