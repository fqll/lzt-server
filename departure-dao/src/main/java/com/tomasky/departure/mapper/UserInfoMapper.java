package com.tomasky.departure.mapper;

import com.tomasky.departure.model.UserInfo;
import com.tomasky.departure.vo.ApproverVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by sam on 2019-08-05.11:01
 */

@Mapper
public interface UserInfoMapper {

    /**
     * 根据公司ID获取公司的管理人员
     *
     * @param companyId
     * @return
     */
    List<ApproverVo> selectManageUserList(Integer companyId);

    /**
     * 根据openId查询用户信息
     *
     * @param openId
     * @return
     */
    UserInfo selectByOpenId(String openId);

    /**
     * 根据公司ID和openId查询员工是否属于该公司
     *
     * @param companyId
     * @param openId
     * @return
     */
    List<UserInfo> selectByCompanyIdAndOpenId(@Param("companyId") Integer companyId, @Param("openId") String openId);

    List<UserInfo> selectUserInfoList();

    /**
     * 根据用户ID集合查询用户信息集合
     *
     * @param userIdList
     * @return
     */
    List<UserInfo> selectByUserIdList(List<Integer> userIdList);

    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}