package com.dream.album.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dreambox.core.service.album.AlbumInfoService;
import com.dreambox.core.service.album.AlbumItemEditInfoService;
import com.dreambox.core.service.album.AlbumItemInfoService;
import com.dreambox.web.action.IosBaseAction;
import com.dreambox.web.model.ApiRespWrapper;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Controller
@RequestMapping("/reload/*")
public class ReloadAction extends IosBaseAction {
    @Autowired
    private AlbumInfoService albumInfoService;
    @Autowired
    private AlbumItemInfoService albumItemInfoService;
    @Autowired
    private AlbumItemEditInfoService albumItemEditInfoService;

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
    @RequestMapping("/album/info.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> reloadAlbumInfo() {
        albumInfoService.cacheInitLoad();
        albumItemInfoService.cacheInitLoad();
        albumItemEditInfoService.cacheInitLoad();
        return new ApiRespWrapper<Boolean>(true);
    }
}
