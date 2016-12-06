package com.dream.album.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dream.album.dto.AlbumTemplatePageInfoModel;
import com.dreambox.core.dto.album.AlbumTemplateInfo;
import com.dreambox.core.service.album.AlbumTemplateInfoService;
import com.dreambox.web.action.IosBaseAction;
import com.dreambox.web.utils.GsonUtils;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Controller
@RequestMapping("/dream/album/template/")
public class AlbumTemplateInfoAction extends IosBaseAction {
    private static final Logger log = Logger.getLogger(AlbumTemplateInfoAction.class);
    @Autowired
    private AlbumTemplateInfoService albumTemplateInfoService;

    @RequestMapping("/add.json")
    private void addTemplateInfo() {
        AlbumTemplateInfo template = new AlbumTemplateInfo();
        template.setTitle("圣诞相册");
        template.setPreImg("http://10.1.1.197:8080/dream-album/images/1/detail/page.png");
        Map<String, String> imgs = new HashMap<String, String>();
        imgs.put("1", "http://10.1.1.197:8080/dream-album/images/1/detail/1.png");
        imgs.put("2", "http://10.1.1.197:8080/dream-album/images/1/detail/2.png");
        imgs.put("3", "http://10.1.1.197:8080/dream-album/images/1/detail/3.png");
        imgs.put("4", "http://10.1.1.197:8080/dream-album/images/1/detail/4.png");
        String imgjson = GsonUtils.toJson(imgs);
        template.setBackgoundImgs(imgjson);
        Map<String, AlbumTemplatePageInfoModel> pageInfos = new HashMap<String, AlbumTemplatePageInfoModel>();
        AlbumTemplatePageInfoModel pageInfo;
        for (int i = 1; i < imgs.size() + 1; i++) {
            pageInfo = new AlbumTemplatePageInfoModel();
            pageInfo.setPositionX(100);
            pageInfo.setPositionY(100);
            pageInfo.setRorate(20);
            pageInfo.setWidth(200);
            pageInfo.setHeight(200);
            pageInfos.put(String.valueOf(i), pageInfo);
        }
        String pages = GsonUtils.toJson(pageInfos);
        template.setPageInfos(pages);
        albumTemplateInfoService.addData(template);
    }
}
