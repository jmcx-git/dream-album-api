package com.jmcxclub.dream.family.service;

import com.dreambox.web.exception.ServiceException;
import com.jmcxclub.dream.family.model.UserOpenIdInfo;

public interface UserOpenIdInfoService {

    UserOpenIdInfo getUser3rdSesseion(String appleId, String code);

    UserOpenIdInfo getUser3rdSesseion(String code);

    UserOpenIdInfo getInfo(String openId) throws ServiceException;

}
