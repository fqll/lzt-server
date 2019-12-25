package com.tomasky.departure.mapper;

import com.tomasky.departure.model.AuthorityInfo;
import com.tomasky.departure.vo.AuthorityVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by sam on 2019-10-18.10:56
 */

@Mapper
public interface AuthorityInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AuthorityInfo record);

    int insertSelective(AuthorityInfo record);

    AuthorityInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuthorityInfo record);

    int updateByPrimaryKey(AuthorityInfo record);

    /**
     * 查询全部功能权限列表
     * @return
     */
    List<AuthorityVo> selectAuthorityVoList();

    /**
     * 查询全部功能权限
     * @return
     */
    List<AuthorityInfo> selectAllAuthorityInfo();
}