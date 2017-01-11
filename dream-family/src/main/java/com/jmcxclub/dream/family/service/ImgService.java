// Copyright 2017 https://mokous.com Inc. All Rights Reserved.

package com.jmcxclub.dream.family.service;

import org.springframework.web.multipart.MultipartFile;

import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.model.UploadFileSaveResp;

/**
 * @author mokous86@gmail.com create date: Jan 10, 2017
 *
 */
public interface ImgService {

    UploadFileSaveResp saveSpaceIcon(MultipartFile image, String openId) throws ServiceException;

    UploadFileSaveResp saveFeedImg(MultipartFile image, String openId) throws ServiceException;

}
