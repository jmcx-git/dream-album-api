package com.dream.album.service;

import com.dream.album.model.UserOpenIdInfo;
import com.dreambox.web.exception.ServiceException;

public interface UserOpenIdInfoService {

    UserOpenIdInfo getUser3rdSesseion(String appleId, String code);

    UserOpenIdInfo getUser3rdSesseion(String code);

    UserOpenIdInfo getInfo(String openId) throws ServiceException;

}
