package com.jmcxclub.dream.family.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dreambox.core.service.AbsCommonCacheDataLoadService;
import com.dreambox.web.action.IosBaseAction;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.utils.CollectionUtils;
import com.jmcxclub.dream.family.service.ActivityInfoService;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Controller
@RequestMapping("/space/reload/*")
public class ReloadAction extends IosBaseAction {
    @Autowired
    private Collection<AbsCommonCacheDataLoadService<?>> loadServices;

    /**
     * 用户点击开始制作创建的用户相册信息数据
     * 
     * 若第一次制作则添加制作信息
     * 
     * 若存在制作中的记录则返回item操作历史
     * 
     * @param userId
     * @param albumId
     */
    @RequestMapping("/data.json")
    @ResponseBody
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ApiRespWrapper<Boolean> reload() {
        if (CollectionUtils.notEmptyAndNull(loadServices)) {
            List<AbsCommonCacheDataLoadService> innerLoadServices = new ArrayList<AbsCommonCacheDataLoadService>(
                    loadServices);
            Collections.sort(innerLoadServices);
            for (AbsCommonCacheDataLoadService absCommonCacheDataLoadService : innerLoadServices) {
                absCommonCacheDataLoadService.cacheInitLoad();
            }
        }
        return new ApiRespWrapper<Boolean>(true);
    }

    /**
     * 用户点击开始制作创建的用户相册信息数据
     * 
     * 若第一次制作则添加制作信息
     * 
     * 若存在制作中的记录则返回item操作历史
     * 
     * @param userId
     * @param albumId
     */
    @RequestMapping("/activity.json")
    @ResponseBody
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ApiRespWrapper<Boolean> reloadActivity() {
        if (CollectionUtils.notEmptyAndNull(loadServices)) {
            List<AbsCommonCacheDataLoadService> innerLoadServices = new ArrayList<AbsCommonCacheDataLoadService>(
                    loadServices);
            Collections.sort(innerLoadServices);
            for (AbsCommonCacheDataLoadService absCommonCacheDataLoadService : innerLoadServices) {
                if (absCommonCacheDataLoadService instanceof ActivityInfoService) {
                    absCommonCacheDataLoadService.cacheInitLoad();
                }
            }
        }
        return new ApiRespWrapper<Boolean>(true);
    }
}
