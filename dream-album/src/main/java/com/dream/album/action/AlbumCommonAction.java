package com.dream.album.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dream.album.dto.AlbumHomePageModel;
import com.dreambox.core.dto.album.AlbumInfo;
import com.dreambox.core.dto.album.AlbumItemInfo;
import com.dreambox.core.service.album.AlbumInfoService;
import com.dreambox.core.service.album.AlbumItemInfoService;
import com.dreambox.core.utils.ParameterUtils;
import com.dreambox.web.action.IosBaseAction;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Controller
@RequestMapping("/dream/album/common/*")
public class AlbumCommonAction extends IosBaseAction {
    private static final Logger log = Logger.getLogger(AlbumCommonAction.class);
    @Autowired
    private AlbumInfoService albumInfoService;
    @Autowired
    private AlbumItemInfoService albumItemInfoService;

    @RequestMapping("/homepage.json")
    @ResponseBody
    public AlbumHomePageModel getHomepage(String keyword, Integer start, Integer size) {
        start = ParameterUtils.formatStart(start);
        size = ParameterUtils.formatSize(size);
        AlbumHomePageModel model = new AlbumHomePageModel();
        // 获取所有相册信息(目前缓存无效暂用直接用数据库取数据)
        // ListWrapResp<AlbumInfo> searchInfos =
        // albumInfoService.searchInfos(keyword, start, size);
        List<AlbumInfo> infos;
        if (StringUtils.isBlank(keyword)) {
            infos = albumInfoService.listDirectFromDb(null, start, size);
        } else {
            AlbumInfo info = new AlbumInfo();
            info.setKeyword(keyword);
            infos = albumInfoService.listDirectFromDb(info, start, size);
        }
        model.setAlbumList(infos);
        return model;
    }

    @RequestMapping("/editalbum.json")
    @ResponseBody
    public List<AlbumItemInfo> editAlbum(int id) {
        if (id == 0) {
            return null;
        }
        AlbumItemInfo info = new AlbumItemInfo();
        info.setStatus(AlbumItemInfo.STATUS_OK);
        info.setAlbumId(id);
        List<AlbumItemInfo> list = albumItemInfoService.listDirectFromDb(info);
        return list;
    }
}
