package com.tomasky.departure.service;

import com.tomasky.departure.bo.CreateCompanyBo;
import com.tomasky.departure.bo.IncumbentsCountBo;
import com.tomasky.departure.bo.JoinCompanyBo;
import com.tomasky.departure.bo.UserAuditBo;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Created by sam on 2019-08-05.16:04
 */
public interface CompanyService {
    /**
     * 创建公司
     *
     * @param createCompanyBo
     */
    Integer createCompany(CreateCompanyBo createCompanyBo);

    /**
     * 根据用户ID查询用户尚未加入的公司列表
     *
     * @param userId
     * @return
     */
    Map<String, Object> findCompanyInfo(Integer userId, String keyWords);

    /**
     * 加入公司
     *
     * @param joinCompanyBo
     */
    void joinCompany(JoinCompanyBo joinCompanyBo);

    /**
     * 根据公司ID查询待加入公司的待审批人员列表
     *
     * @param companyId
     * @param auditStatus
     * @param nickName
     * @return
     */
    Map<String, Object> getAuditUserList(Integer companyId, String auditStatus, String nickName, String mode);

    /**
     * 根据用户ID、公司ID、审批结果，修改用户加入公司的审批状态
     *
     * @param userAuditBo
     */
    void auditUser(UserAuditBo userAuditBo);

    /**
     * 邀请加入公司
     *
     * @param userAuditBo
     */
    void inviteUser(UserAuditBo userAuditBo);

    /**
     * 校验员工姓名在企业中的唯一性
     *
     * @param companyId
     * @param nickName
     * @param employeeId
     * @return
     */
    Map<String, Object> checkNameUnique(Integer companyId, String nickName, Integer employeeId);

    /**
     * 根据公司ID生成公司的小程序码
     *
     * @param companyId
     * @return
     */
    ResponseEntity<byte[]> findCompanyImage(Integer companyId);

    /**
     * 更新员工角色信息
     *
     * @param userAuditBo
     */
    void updateRole(UserAuditBo userAuditBo);

    /**
     * 根据公司ID查询公司详情
     *
     * @param companyId
     * @return
     */
    Map<String, Object> findCompanyInfo(Integer companyId);

    /**
     * 设置主企业
     *
     * @param joinCompanyBo
     */
    void setDefault(JoinCompanyBo joinCompanyBo);

    /**
     * 修改在职人数
     *
     * @param incumbentsCountBo
     */
    void modifyIncumbentsCount(IncumbentsCountBo incumbentsCountBo);
}
