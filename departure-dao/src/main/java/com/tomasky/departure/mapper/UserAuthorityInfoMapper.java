package com.tomasky.departure.mapper;

import com.tomasky.departure.model.UserAuthorityInfo;
import com.tomasky.departure.vo.AuthorityVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by sam on 2019-10-18.14:36
 */

@Mapper
public interface UserAuthorityInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAuthorityInfo record);

    int insertSelective(UserAuthorityInfo record);

    UserAuthorityInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAuthorityInfo record);

    int updateByPrimaryKey(UserAuthorityInfo record);

    /**
     * 根据用户ID和公司ID查询权限集合
     *
     * @param userId
     * @param companyId
     * @return
     */
    List<AuthorityVo> selectUserAuthorityInfo(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 根据用户ID和权限ID，删除权限关联关系
     * @param userId
     * @param companyId
     */
    void deleteUserAuthorityInfo(@Param("userId") Integer userId, @Param("companyId") Integer companyId);
}