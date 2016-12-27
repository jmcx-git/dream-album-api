package com.dream.album.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dream.album.model.UserOpenIdInfo;
import com.dream.album.service.UserOpenIdInfoService;
import com.dreambox.core.dto.album.SmallAppDeveloperInfo;
import com.dreambox.core.service.album.SmallAppDeveloperInfoService;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.utils.GsonUtils;
import com.dreambox.web.utils.RemoteDataUtil;

@Service("userOpenIdInfoService")
public class UserOpenIdInfoServiceImpl implements UserOpenIdInfoService {
    public static Logger log = Logger.getLogger(UserOpenIdInfoServiceImpl.class);
    public static String GRANT_TYPE = "authorization_code";
    @Autowired
    private SmallAppDeveloperInfoService smallAppDeveloperInfoService;

    @Override
    public UserOpenIdInfo getUser3rdSesseion(String code) {
        return getUser3rdSesseion(null, code);
    }

    @Override
    public UserOpenIdInfo getUser3rdSesseion(String appId, String code) {
        if (StringUtils.isEmpty(appId)) {
            appId = "wx0ddc8673b8df3827";
        }
        SmallAppDeveloperInfo g = new SmallAppDeveloperInfo();
        g.setAppId(appId);
        g = smallAppDeveloperInfoService.getInfoByUk(g);
        if (g == null) {
            throw ServiceException.getInternalException("Unknown appid for wechat small app developer.");
        }
        return getUser3rdSesseion(code, g);
    }

    private UserOpenIdInfo getUser3rdSesseion(String code, SmallAppDeveloperInfo g) {
        String api = "https://api.weixin.qq.com/sns/jscode2session?appid=" + g.getAppId() + "&secret=" + g.getSecret()
                + "&js_code=" + code + "&grant_type=" + GRANT_TYPE;
        String ret = null;
        try {
            ret = RemoteDataUtil.getRemote(api, 5000, 3);
        } catch (Exception e) {
            throw ServiceException.getInternalException("Get user session code failed. Network error.");
        }
        if (ret == null) {
            return null;
        } else {
            try {
                UserOpenIdInfo info = GsonUtils.convert(ret, UserOpenIdInfo.class);
                if (info != null) {
                    return info;
                }
            } catch (Exception e) {
                throw ServiceException.getInternalException("Convert user session code failed. Convert error.");
            }
        }
        return null;
    }
}
