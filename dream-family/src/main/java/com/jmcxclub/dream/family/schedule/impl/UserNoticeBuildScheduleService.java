// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.schedule.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.dreambox.core.CronMonitor;
import com.dreambox.core.service.AbstractScheduleService;
import com.jmcxclub.dream.family.dto.ActivityInfo;
import com.jmcxclub.dream.family.dto.ActivityUserPrizeInfo;
import com.jmcxclub.dream.family.dto.ActivityWorksInfo;
import com.jmcxclub.dream.family.dto.NoticeBuildEnum;
import com.jmcxclub.dream.family.dto.PrizeInfo;
import com.jmcxclub.dream.family.dto.UserNoticeInfo;
import com.jmcxclub.dream.family.dto.UserNoticeReadEnum;
import com.jmcxclub.dream.family.model.AboutUsResp;
import com.jmcxclub.dream.family.service.ActivityInfoService;
import com.jmcxclub.dream.family.service.ActivityPrizeInfoService;
import com.jmcxclub.dream.family.service.ActivityUserPrizeInfoService;
import com.jmcxclub.dream.family.service.ActivityWorksInfoService;
import com.jmcxclub.dream.family.service.PrizeInfoService;
import com.jmcxclub.dream.family.service.UserNoticeInfoService;
import com.jmcxclub.dream.family.utils.ContentDescUtils;

/**
 * @author mokous86@gmail.com create date: Jan 12, 2017
 * 
 *         现阶段只构建中奖信息
 */
@Service("userNoticeBuildScheduleService")
public class UserNoticeBuildScheduleService extends AbstractScheduleService {
    private static final Logger log = Logger.getLogger(UserNoticeBuildScheduleService.class);
    @Value("${dream.family.usernoticebuild.stop}")
    private volatile boolean stop = true;
    @Autowired
    private ActivityInfoService activityInfoService;
    @Autowired
    private ActivityPrizeInfoService activityPrizeInfoService;
    @Autowired
    private ActivityUserPrizeInfoService activityUserPrizeInfoService;
    @Autowired
    private UserNoticeInfoService userNoticeInfoService;
    @Autowired
    private PrizeInfoService prizeInfoService;
    @Autowired
    private ActivityWorksInfoService activityWorksInfoService;

    @PreDestroy
    public void shutdown() {
        this.stop = true;
    }

    @Scheduled(cron = "0 */5 * * * ?")
    @Override
    public void schedule() {
        if (stop) {
            return;
        }
        try {
            innerBuild();
        } catch (Exception e) {
            log.error("Execute user notice build failed.Errmsg:" + e.getMessage(), e);
        }
    }

    private void innerBuild() {
        int size = 100;
        int startId = activityUserPrizeInfoService.minUnbuildId();
        if (startId <= 0) {
            return;
        }
        // query excludive id
        startId = startId - 1;
        List<UserNoticeInfo> userNoticeInfos;
        while (!stop) {
            List<ActivityUserPrizeInfo> datas = activityUserPrizeInfoService.listByStartIdDirectFromDb(null, startId,
                    size);
            if (datas == null || datas.isEmpty()) {
                log.info("No more data need to build notice.");
                break;
            }
            userNoticeInfos = new ArrayList<UserNoticeInfo>();
            for (ActivityUserPrizeInfo data : datas) {
                data.setBuild(NoticeBuildEnum.BUILDED.getFlag());
                PrizeInfo prizeInfo = prizeInfoService.getData(data.getPrizeId());
                if (prizeInfo == null) {
                    log.warn("The prize info dose not exists.PrizeId:" + data.getPrizeId());
                    continue;
                }
                ActivityInfo activityInfo = activityInfoService.getData(data.getActivityId());
                if (activityInfo == null) {
                    log.warn("The activity info dose not exists.ActivityId:" + data.getActivityId());
                    continue;
                }
                ActivityWorksInfo activityWorksInfo = activityWorksInfoService.getData(data.getWorksId());
                if (activityWorksInfo == null) {
                    log.warn("The activity works dose not exists.WorksId:" + data.getWorksId());
                    continue;
                }
                String desc = ContentDescUtils.buildNotice(prizeInfo, activityInfo, activityWorksInfo);
                UserNoticeInfo userNoticeInfo = new UserNoticeInfo();
                userNoticeInfo.setDesc(desc);
                userNoticeInfo.setUserId(data.getUserId());
                userNoticeInfo.setRead(UserNoticeReadEnum.INIT.getFlag());
                userNoticeInfo.setImgUrl(null);
                userNoticeInfos.add(userNoticeInfo);
            }
            userNoticeInfoService.batchAdd(userNoticeInfos);
            activityUserPrizeInfoService.modifyBuild(datas);
            if (datas.size() < size) {
                break;
            }
            startId = datas.get(datas.size() - 1).getId();
        }
    }

    @Override
    public String getCron() {
        return "0 0 */1 * * ?";
    }

    @Override
    public String getLastMessage() {
        return super.getLastMessage();
    }

    @Override
    public Date getLastActiveTime() {
        return super.getLastActiveTime();
    }

    @Override
    public boolean isStop() {
        return this.stop;
    }

    @Override
    public CronMonitor getMonitor() {
        return super.getMonitor();
    }

}
