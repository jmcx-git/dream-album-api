// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dreambox.core.LocalRes;
import com.dreambox.web.exception.ServiceException;

/**
 * 本地资源存储类
 * 
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public interface LocalResService {
    /**
     * 
     * @param jsonRemoteIconUrls
     * @return
     * @throws ServiceException
     */
    public List<String> saveLocalIcons(String jsonRemoteUrls) throws ServiceException;

    public List<String> saveLocalIpadScreens(String jsonRemoteUrls) throws ServiceException;

    public List<String> saveLocalIphoneScreens(String jsonRemoteUrls) throws ServiceException;

    public String saveLocalIpa(String ipaUrl) throws ServiceException;

    public List<LocalRes> copyLocalIconsToWebsitePath(int rootId, List<String> iconPathes) throws ServiceException;

    public List<LocalRes> copyLocalIphoneScreensToWebsitePath(int rootId, List<String> iphoenScreenPathes)
            throws ServiceException;

    public List<LocalRes> copyLocalIpadScreensToWebsitePath(int rootId, List<String> ipadScreenPathes)
            throws ServiceException;

    public String saveLocalIpadScreens(List<CommonsMultipartFile> ipad) throws ServiceException;

    public String saveLocalIcons(List<CommonsMultipartFile> icon) throws ServiceException;

    public String saveLocalIphoneScreens(List<CommonsMultipartFile> iphone) throws ServiceException;

    public String saveColumnImg(String urlPath, File file) throws ServiceException;

    public String saveColumnImg(String url, String path, String fileName) throws ServiceException;
}
