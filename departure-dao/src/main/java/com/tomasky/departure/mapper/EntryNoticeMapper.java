package com.tomasky.departure.mapper;

import com.tomasky.departure.model.EntryNotice;
import org.apache.ibatis.annotations.Mapper;

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
}