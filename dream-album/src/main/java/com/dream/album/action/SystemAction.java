package com.dream.album.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dream.album.service.DeveloperServerService;
import com.dreambox.web.action.IosBaseAction;
import com.dreambox.web.model.ApiRespWrapper;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Controller
@RequestMapping("/system/*")
public class SystemAction extends IosBaseAction {
    @Autowired
    private DeveloperServerService developerServerService;

    @RequestMapping("/developer/server/restart.json")
    @ResponseBody
    public ApiRespWrapper<Boolean> restart() {
        return developerServerService.restart();
    }

}
