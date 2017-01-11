// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import java.util.Date;

import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.model.ListWrapResp;
import com.jmcxclub.dream.family.model.OccupantFootprintResp;
import com.jmcxclub.dream.family.model.SpaceInfoResp;
import com.jmcxclub.dream.family.model.SpaceListResp;
import com.jmcxclub.dream.family.model.SpaceFeedCommentListResp;
import com.jmcxclub.dream.family.model.SpaceFeedListResp;
import com.jmcxclub.dream.family.model.SpaceFeedResp;

/**
 * @author mokous86@gmail.com create date: Jan 9, 2017
 *
 */
public interface SpaceService {
    /**
     * 返回用户自己及加入的空间列表信息
     * 
     * @param openId
     * @param start
     * @param size
     * @return
     * @throws ServiceException
     */
    ApiRespWrapper<ListWrapResp<SpaceListResp>> listSpace(String openId, int start, int size) throws ServiceException;

    ApiRespWrapper<SpaceInfoResp> getSpaceInfo(String openId, int spaceId) throws ServiceException;

    /**
     * 返回具体空间的feed列表
     * 
     * @param openId
     * @param start
     * @param size
     * @param size2
     * @return
     * @throws ServiceException
     */
    ApiRespWrapper<ListWrapResp<SpaceFeedListResp>> listSpaceFeed(String openId, int spaceId, int start, int size)
            throws ServiceException;

    /**
     * 对空间feed添加评论,支持emoji,返回生成的评论的id
     * 
     * 如果commentRefId>0则说明此条评论是针对某条评论的
     * 
     * @param openId
     * @param feedId
     * @param commentRefId
     * @param comment
     * @return
     * @throws ServiceException
     */
    ApiRespWrapper<Integer> addFeedComment(String openId, int feedId, Integer commentRefId, String comment)
            throws ServiceException;

    /**
     * 点赞或取消空间点赞 0:赞 -1:取消点赞
     * 
     * @param openId
     * @param feedId
     * @param status
     * @return
     * @throws ServiceException
     */
    ApiRespWrapper<Boolean> likeFeed(String openId, int feedId, int status) throws ServiceException;

    /**
     * 用户添加空间
     * 
     * @param openId
     * @param title
     * @return
     * @throws ServiceException
     */
    ApiRespWrapper<Integer> addSpace(String openId, Integer gender, String name, Date bornDate, int type, String icon,
            String cover, String info) throws ServiceException;

    /**
     * 用户删除空间
     * 
     * @param openId
     * @param spaceId
     * @return
     * @throws ServiceException
     */
    ApiRespWrapper<Boolean> deleteSpace(String openId, int spaceId) throws ServiceException;

    /**
     * 用户编辑空间
     * 
     * @param openId
     * @param spaceId
     * @param name
     * @param born
     * @return
     * @throws ServiceException
     */
    ApiRespWrapper<Boolean> editSpace(String openId, int spaceId, String name, Date born) throws ServiceException;

    /**
     * 用户编辑空间
     * 
     * @param openId
     * @param spaceId
     * @param name
     * @param born
     * @return
     * @throws ServiceException
     */
    ApiRespWrapper<Boolean> editSpaceIcon(String openId, int spaceId, String icon) throws ServiceException;

    /**
     * 用户加入空间
     * 
     * @param openId
     * @param secert
     * @return
     * @throws ServiceException
     */
    ApiRespWrapper<Integer> joinSpace(String openId, String secert) throws ServiceException;

    /**
     * 用户生成空间加密码
     * 
     * @param openId
     * @param spaceId
     * @return
     * @throws ServiceException
     */
    ApiRespWrapper<String> secertSpace(String openId, int spaceId) throws ServiceException;


    // ////////以下方法暂时不用实现

    /**
     * 返回具体空间的feed详情信息----CUR DOSE NOT NEED IMPLEMENTS
     * 
     * @param openId
     * @param feedId
     * @return
     * @throws ServiceException
     */
    ApiRespWrapper<SpaceFeedResp> getFeedDetail(String openId, int feedId) throws ServiceException;

    /**
     * 返回空间的具体feed的评论列表----CUR DOSE NOT NEED IMPLEMENTS
     * 
     * @param openId
     * @param feedId
     * @param headCommentId
     * @param start
     * @param size
     * @return
     * @throws ServiceException
     */
    ApiRespWrapper<ListWrapResp<SpaceFeedCommentListResp>> listFeedComment(String openId, int feedId,
            Integer headCommentId, Integer start, Integer size) throws ServiceException;

    /**
     * 删除具体feed下的评论,评论作者或feed作者可以删除----CUR DOSE NOT NEED IMPLEMENTS
     * 
     * @param openId
     * @param feedId
     * @param commentId
     * @return
     * @throws ServiceException
     */
    ApiRespWrapper<Boolean> deleteFeedComment(String openId, int feedId, int commentId) throws ServiceException;

    ApiRespWrapper<ListWrapResp<OccupantFootprintResp>> listSpaceOccupantFootprint(String openId, int spaceId)
            throws ServiceException;

    ApiRespWrapper<Integer> addFeed(int userId, int spaceId, String title, String content, int type, String cover,
            String illustration) throws ServiceException;
}
