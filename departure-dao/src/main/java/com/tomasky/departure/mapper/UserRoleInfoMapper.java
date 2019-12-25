package com.tomasky.departure.mapper;

import com.tomasky.departure.bo.JoinCompanyBo;
import com.tomasky.departure.model.UserRoleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by sam on 2019-08-06.15:31
 */

@Mapper
public interface UserRoleInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRoleInfo record);

    int insertSelective(UserRoleInfo record);

    UserRoleInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRoleInfo record);

    int updateByPrimaryKey(UserRoleInfo record);

    /**
     * 根据公司ID和用户昵称查询该名称在该公司出现的次数
     *
     * @param companyId
     * @param nickName
     * @param employeeId
     * @return
     */
    int selectNickNameCount(@Param("companyId") Integer companyId, @Param("nickName") String nickName, @Param("employeeId") Integer employeeId);

    /**
     * 根据公司ID和昵称查询是否有存在相同昵称的员工
     *
     * @param companyId
     * @param nickName
     * @return
     */
    int selectByNickName(@Param("companyId") Integer companyId, @Param("nickName") String nickName);

    /**
     * 根据公司ID和用户ID查询加入的公司信息
     *
     * @param joinCompanyBo
     * @return
     */
    List<UserRoleInfo> selectUserRoleInfoListByCompanyAndUser(JoinCompanyBo joinCompanyBo);

    /**
     * 根据用户ID和公司ID查询角色信息
     *
     * @param userId
     * @param companyId
     * @return
     */
    UserRoleInfo selectByUserIdAndCompanyId(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 根据公司ID和员工姓名查询员工信息
     *
     * @param companyId
     * @param nickName
     * @return
     */
    List<UserRoleInfo> selectUserRoleInfo(@Param("companyId") Integer companyId, @Param("nickName") String nickName, @Param("auditStatus") String auditStatus);

    /**
     * 根据用户ID、公司ID、入住状态查询公司列表
     *
     * @param joinCompanyBo
     * @return
     */
    List<UserRoleInfo> selectCompanyInfoList(JoinCompanyBo joinCompanyBo);

    /**
     * 设置主企业
     *
     * @param userId
     * @param companyId
     */
    void setDefaultCompany(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 设置其他企业为非主企业
     *
     * @param userId
     * @param companyId
     */
    void setNotDefaultCompany(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 审核拒绝删除数据
     *
     * @param userId
     * @param companyId
     */
    void deleteRefuseRecord(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 根据用户ID公司ID查询待审批加入公司的数据条数
     *
     * @param companyId
     * @return
     */
    int selectAuthorityCount(@Param("companyId") Integer companyId);
}