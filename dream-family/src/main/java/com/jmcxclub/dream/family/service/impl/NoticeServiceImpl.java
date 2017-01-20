// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreambox.core.cache.CacheFilter.StartSizeCacheFilter;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.model.ListWrapResp;
import com.dreambox.web.utils.CollectionUtils;
import com.jmcxclub.dream.family.dto.SystemNoticeInfo;
import com.jmcxclub.dream.family.dto.UserNoticeInfo;
import com.jmcxclub.dream.family.dto.UserReadNoticeRecord;
import com.jmcxclub.dream.family.dto.WikiInfo;
import com.jmcxclub.dream.family.model.NoticeResp;
import com.jmcxclub.dream.family.model.WikiResp;
import com.jmcxclub.dream.family.service.NoticeService;
import com.jmcxclub.dream.family.service.SystemNoticeInfoService;
import com.jmcxclub.dream.family.service.UserNoticeInfoService;
import com.jmcxclub.dream.family.service.WikiInfoService;
import com.jmcxclub.dream.family.service.UserNoticeInfoService.UserNoticeInfoSortedSetCacheFilter;
import com.jmcxclub.dream.family.service.UserReadNoticeRecordService;

/**
 * @author mokous86@gmail.com create date: Jan 12, 2017
 *
 */
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private UserNoticeInfoService userNoticeInfoService;
    @Autowired
    private SystemNoticeInfoService systemNoticeInfoService;
    @Autowired
    private UserReadNoticeRecordService userReadNoticeRecordService;
    @Autowired
    private WikiInfoService wikiInfoService;

    @Override
    public boolean hasNotice(int userId) throws ServiceException {
        UserReadNoticeRecord record = userReadNoticeRecordService.getData(userId);
        Date readTime = null;
        if (record != null) {
            readTime = record.getReadTime();
        }
        StartSizeCacheFilter filter = new StartSizeCacheFilter();
        filter.setSize(1);
        ListWrapResp<SystemNoticeInfo> systemNoticeResp = systemNoticeInfoService.listInfo(filter);
        if (systemNoticeResp != null && CollectionUtils.notEmptyAndNull(systemNoticeResp.getResultList())) {
            if (readTime == null) {
                return true;
            }
            if (readTime.before(systemNoticeResp.getResultList().get(0).getCreateTime())) {
                return true;
            }
        }
        ListWrapResp<UserNoticeInfo> usrNoticeResp = userNoticeInfoService
                .listInfo(new UserNoticeInfoSortedSetCacheFilter(userId, 0, 1));
        if (usrNoticeResp != null && CollectionUtils.notEmptyAndNull(usrNoticeResp.getResultList())) {
            if (readTime == null) {
                return true;
            }
            if (readTime.before(usrNoticeResp.getResultList().get(0).getCreateTime())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ApiRespWrapper<ListWrapResp<NoticeResp>> listNotice(int userId, Integer startId, Integer type, int size)
            throws ServiceException {
        StartSizeCacheFilter filter = new StartSizeCacheFilter();
        filter.setSize(Integer.MAX_VALUE);
        ListWrapResp<SystemNoticeInfo> systemNoticeResp = systemNoticeInfoService.listInfo(filter);
        ListWrapResp<UserNoticeInfo> usrNoticeResp = userNoticeInfoService
                .listInfo(new UserNoticeInfoSortedSetCacheFilter(userId));
        List<NoticeResp> allNotices = new ArrayList<NoticeResp>();
        if (usrNoticeResp != null && CollectionUtils.notEmptyAndNull(usrNoticeResp.getResultList())) {
            for (UserNoticeInfo userNoticeInfo : usrNoticeResp.getResultList()) {
                allNotices.add(new NoticeResp(userNoticeInfo));
            }
        }
        if (systemNoticeResp != null && CollectionUtils.notEmptyAndNull(systemNoticeResp.getResultList())) {
            for (SystemNoticeInfo systemNoticeInfo : systemNoticeResp.getResultList()) {
                allNotices.add(new NoticeResp(systemNoticeInfo));
            }
        }
        UserReadNoticeRecord userReadNoticeRecord = new UserReadNoticeRecord();
        userReadNoticeRecord.setId(userId);
        userReadNoticeRecord.setReadTime(new Date());
        userReadNoticeRecordService.addData(userReadNoticeRecord);
        if (CollectionUtils.emptyOrNull(allNotices)) {
            return new ApiRespWrapper<ListWrapResp<NoticeResp>>(new ListWrapResp<NoticeResp>(
                    new ArrayList<NoticeResp>()));
        }
        Collections.sort(allNotices, new Comparator<NoticeResp>() {
            @Override
            public int compare(NoticeResp o1, NoticeResp o2) {
                if (o2.getTime() == o1.getTime()) {
                    return o2.getId() - o1.getId();
                }
                return (int) (o2.getTime() - o1.getTime());
            }
        });
        List<NoticeResp> datas = null;
        boolean more;
        if ((startId == null || startId == 0) && type == null) {
            if (allNotices.size() < size) {
                datas = allNotices;
                more = false;
            } else {
                datas = allNotices.subList(0, size);
                more = true;
            }
            return new ApiRespWrapper<ListWrapResp<NoticeResp>>(
                    new ListWrapResp<>(allNotices.size(), datas, more, size));
        } else {
            int startIndex = 0;
            for (NoticeResp noticeResp : allNotices) {
                if (noticeResp.getType() == type.intValue() && noticeResp.getId() == startId) {
                    // find the next will user
                    break;
                }
                startIndex++;
            }
            if (allNotices.size() <= startIndex + 1) {
                return new ApiRespWrapper<ListWrapResp<NoticeResp>>(new ListWrapResp<NoticeResp>(allNotices.size(),
                        null, false, allNotices.size()));
            }
            more = allNotices.size() > (startIndex + 1 + size);
            int endIndex = more ? startIndex + 1 + size : allNotices.size();
            datas = allNotices.subList(startIndex + 1, endIndex);
            return new ApiRespWrapper<ListWrapResp<NoticeResp>>(new ListWrapResp<>(allNotices.size(), datas, more,
                    startIndex + datas.size()));

        }
    }

    @Override
    public List<WikiResp> listWikis() throws ServiceException {
        StartSizeCacheFilter filter = new StartSizeCacheFilter();
        filter.setSize(Integer.MAX_VALUE);
        ListWrapResp<WikiInfo> wikis = wikiInfoService.listInfo(filter);
        List<WikiResp> ret = new ArrayList<>();
        if (wikis != null && CollectionUtils.notEmptyAndNull(wikis.getResultList())) {
            List<WikiInfo> infos = wikis.getResultList();
            for (WikiInfo wikiInfo : infos) {
                ret.add(new WikiResp(wikiInfo, true));
            }
        }
        return ret;
    }

    @Override
    public WikiResp getWiki(int id) throws ServiceException {
        WikiInfo wiki = wikiInfoService.getData(id);
        return new WikiResp(wiki, false);
    }
}
