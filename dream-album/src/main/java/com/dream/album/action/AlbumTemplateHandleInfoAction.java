package com.dream.album.action;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dreambox.web.action.IosBaseAction;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Controller
@RequestMapping("/dream/album/handle/")
public class AlbumTemplateHandleInfoAction extends IosBaseAction {
    private static final Logger log = Logger.getLogger(AlbumTemplateHandleInfoAction.class);

    @RequestMapping("/get.json")
    private void createMenu() {
        try {
        } catch (Exception e) {
            log.error("create wechat menu error:" + e.getMessage());
        }
    }
}
