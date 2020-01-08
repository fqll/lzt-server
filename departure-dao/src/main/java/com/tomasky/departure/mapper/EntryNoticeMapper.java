package com.tomasky.departure.mapper;

import com.tomasky.departure.model.EntryNotice;
import com.tomasky.departure.vo.EntryNoticeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by sam on 2020-01-02.15:44
 */

@Mapper
public interface EntryNoticeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EntryNotice record);

    int insertSelective(EntryNotice record);

    EntryNotice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EntryNotice record);

    int updateByPrimaryKey(EntryNotice record);

    /**
     * 查询已发送入职通知列表
     *
     * @param userId
     * @param companyId
     * @return
     */
    List<EntryNoticeVo> selectEntryNoticeVoList(@Param("userId") Integer userId, @Param("companyId") Integer companyId);
}