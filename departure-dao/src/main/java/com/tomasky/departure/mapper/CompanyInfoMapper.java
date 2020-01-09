package com.tomasky.departure.mapper;

import com.tomasky.departure.model.CompanyInfo;
import com.tomasky.departure.vo.ApproverUserVo;
import com.tomasky.departure.vo.CompanyInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by sam on 2019-08-05.11:16
 */

@Mapper
public interface CompanyInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompanyInfo record);

    int insertSelective(CompanyInfo record);

    CompanyInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompanyInfo record);

    int updateByPrimaryKey(CompanyInfo record);

    /**
     * 根据用户ID查询所属公司信息
     *
     * @param userId
     * @return
     */
    List<CompanyInfoVo> selectByUserId(@Param("userId") Integer userId, @Param("jobStatus") String jobStatus);

    /**
     * 根据用户ID和公司查询所属公司信息
     *
     * @param userId
     * @param companyId
     * @return
     */
    CompanyInfoVo selectByUserIdAndCompanyId(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 根据用户ID和搜索关键字查询用户尚未加入的公司列表
     *
     * @param userId
     * @param companyId
     * @return
     */
    List<CompanyInfo> selectByKeyWords(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 根据公司名称和统一信用代码查询公司列表
     *
     * @param companyName
     * @param creditCode
     * @return
     */
    List<CompanyInfo> selectByCredit(@Param("companyName") String companyName, @Param("creditCode") String creditCode);

    /**
     * 查询公司的审批人列表
     *
     * @param companyId
     * @param nickName
     * @param auditStatus
     * @return
     */
    List<ApproverUserVo> selectCompanyUserList(@Param("companyId") Integer companyId, @Param("nickName") String nickName, @Param("auditStatus") String auditStatus);

    /**
     * 查询邀请加入公司的人员列表
     *
     * @param companyId
     * @param nickName
     * @return
     */
    List<ApproverUserVo> selectInviteList(@Param("companyId") Integer companyId, @Param("nickName") String nickName);


}