package com.dream.album.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/homepage.json")
    public void get() {

    }

}
