package com.tomasky.departure.mapper;

import com.tomasky.departure.model.ChatLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by sam on 2019-08-16.14:35
 */

@Mapper
public interface ChatLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatLog record);

    int insertSelective(ChatLog record);

    ChatLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatLog record);

    int updateByPrimaryKey(ChatLog record);

    /**
     * 根据离职表单ID查询聊天记录
     *
     * @param departureId
     * @return
     */
    List<ChatLog> selectChatLogListByDepartureId(Integer departureId);

    /**
     * 根据离职表单ID查询全部参与聊天的用户ID集合
     *
     * @param departureId
     * @param userId
     * @return
     */
    List<Integer> selectOtherUserIdListInChat(@Param("departureId") Integer departureId, @Param("userId") Integer userId);

    /**
     * 根据离职表单ID和上家公司关注人ID查询聊天记录
     *
     * @param departureId
     * @return
     */
    int selectChatLogListSize(@Param("departureId") Integer departureId);
}