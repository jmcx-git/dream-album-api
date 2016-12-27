package com.dream.album.service;

import com.dream.album.model.UserOpenIdInfo;

public interface UserOpenIdInfoService {

    UserOpenIdInfo getUser3rdSesseion(String appleId, String code);

    UserOpenIdInfo getUser3rdSesseion(String code);

}
