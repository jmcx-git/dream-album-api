package com.dream.album.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dreambox.core.dto.album.AlbumInfo;
import com.dreambox.core.service.album.AlbumInfoService;
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

    @RequestMapping("/homepage.json")
    @ResponseBody
    public List<AlbumInfo> getHomepage(String userId) {
        List<AlbumInfo> list = albumInfoService.listDirectFromDb(null);
        return list;
    }
}
