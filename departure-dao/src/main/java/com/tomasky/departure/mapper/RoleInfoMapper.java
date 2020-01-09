package com.tomasky.departure.mapper;

import com.tomasky.departure.model.RoleInfo;
import com.tomasky.departure.vo.AuthorityVo;
import com.tomasky.departure.vo.RoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by sam on 2019-08-05.11:16
 */

@Mapper
public interface RoleInfoMapper {

    /**
     * 查询全部角色列表
     *
     * @return
     */
    List<RoleInfo> selectAll();

    /**
     * 根据角色ID查询权限列表
     *
     * @param roleId
     * @return
     */
    List<AuthorityVo> selectAuthorityListByRoleId(Integer roleId);

    /**
     * 根据用户ID和公司ID查询角色信息
     *
     * @param userId
     * @param companyId
     * @return
     */
    RoleVo selectRoleVoByUserIdAndCompanyId(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 根据角色ID查询角色名称
     *
     * @param roleId
     * @return
     */
    RoleVo selectRoleName(Integer roleId);

    int deleteByPrimaryKey(Integer id);

    int insert(RoleInfo record);

    int insertSelective(RoleInfo record);

    RoleInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleInfo record);

    int updateByPrimaryKey(RoleInfo record);
}