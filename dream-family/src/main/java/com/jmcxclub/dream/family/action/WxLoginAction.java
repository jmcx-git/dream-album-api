package com.jmcxclub.dream.family.action;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.service.album.UserInfoService;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.utils.GsonUtils;
import com.jmcxclub.dream.family.model.UserFullInfo;
import com.jmcxclub.dream.family.model.UserInfoResp;
import com.jmcxclub.dream.family.model.UserOpenIdInfo;
import com.jmcxclub.dream.family.service.UserOpenIdInfoService;
import com.jmcxclub.dream.family.utils.AES;

/**
 * 
 * @author yhx
 *
 */
@Controller
@RequestMapping("/space/user/*")
public class WxLoginAction {
    public static Logger log = Logger.getLogger(WxLoginAction.class);
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserOpenIdInfoService userOpenIdInfoService;

    @RequestMapping("/session.json")
    @ResponseBody
    public String getUser3rdSesseion(String appId, String code, String fromOpenId) {
        UserOpenIdInfo info = userOpenIdInfoService.getUser3rdSesseion(appId, code);
        log.info("The from open id " + fromOpenId + " share a new user." + info.getOpenid());
        return info.getOpenid();
    }

    @RequestMapping("/getShareUserInfo.json")
    @ResponseBody
    public ApiRespWrapper<UserInfoResp> getShareUserInfo(String openId, String appId) {
        if (StringUtils.isAnyEmpty(openId, appId)) {
            return new ApiRespWrapper<UserInfoResp>(-1, "Illegal parameter for get user info.");
        }
        UserInfo g = new UserInfo();
        g.setOpenId(openId);
        g = userInfoService.getInfoByUk(g);
        if (g == null) {
            return new ApiRespWrapper<UserInfoResp>(-1, "Illegal openid for weixin user.");
        }
        return new ApiRespWrapper<UserInfoResp>(new UserInfoResp(g));
    }

    @RequestMapping("/info.json")
    @ResponseBody
    public ApiRespWrapper<UserInfoResp> getInfo(String openId, String encryptedData, String iv, String appId) {
        if (StringUtils.isAnyEmpty(openId, encryptedData, iv)) {
            return new ApiRespWrapper<UserInfoResp>(-1, "Illegal parameter for get user info.");
        }
        UserOpenIdInfo userOpenIdInfo = userOpenIdInfoService.getInfo(openId);
        if (userOpenIdInfo == null) {
            return new ApiRespWrapper<UserInfoResp>(-1, "Illegal openid for weixin user.");
        }
        String userInfoStr = null;
        try {
            AES aes = new AES();
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData),
                    Base64.decodeBase64(userOpenIdInfo.getSession_key()), Base64.decodeBase64(iv));
            if (null != resultByte && resultByte.length > 0) {
                userInfoStr = new String(resultByte, "UTF-8");
            }
        } catch (Exception e) {
            log.error("decode userinfo failed." + e.getMessage());
            return new ApiRespWrapper<UserInfoResp>(-1, "Decrypt or convert wexin data failed.");
        }
        if (!StringUtils.isEmpty(userInfoStr)) {
            try {
                UserFullInfo info = GsonUtils.convert(userInfoStr, UserFullInfo.class);
                UserInfo g = new UserInfo();
                g.setOpenId(openId);
                g = userInfoService.getInfoByUk(g);
                if (g == null) {
                    g = new UserInfo();
                }
                BeanUtils.copyProperties(info, g);
                g.setAppid(info.getWatermark().getAppid());
                if (g.getId() == 0) {
                    userInfoService.addData(g);
                } else {
                    userInfoService.modifyUserInfo(g);
                }
                UserInfoResp data = new UserInfoResp(g);
                return new ApiRespWrapper<UserInfoResp>(data);
            } catch (Exception e) {
                log.error("parse userinfo failed." + e.getMessage());
            }
        }
        return new ApiRespWrapper<UserInfoResp>(-1, "Get user info failed.");
    }


    @RequestMapping("/noauthorize/info.json")
    @ResponseBody
    public ApiRespWrapper<UserInfoResp> getNoAuthorizeInfo(String openId, String appId) {
        if (StringUtils.isAnyEmpty(openId)) {
            return new ApiRespWrapper<UserInfoResp>(-1, "Illegal parameter for get user info.");
        }
        UserOpenIdInfo userOpenIdInfo = userOpenIdInfoService.getInfo(openId);
        if (userOpenIdInfo == null) {
            return new ApiRespWrapper<UserInfoResp>(-1, "Illegal openid for weixin user.");
        }
        try {
            UserInfo g = new UserInfo();
            g.setOpenId(openId);
            g = userInfoService.getInfoByUk(g);
            if (g == null) {
                g = new UserInfo();
            }
            g.setAvatarUrl("https://cdn.mokous.com/static/familydefault.png");
            g.setNickName("未授权用户");
            g.setAppid(appId);
            if (g.getId() == 0) {
                userInfoService.addData(g);
            } else {
                userInfoService.modifyUserInfo(g);
            }
            UserInfoResp data = new UserInfoResp(g);
            return new ApiRespWrapper<UserInfoResp>(data);
        } catch (Exception e) {
            log.error("parse userinfo failed." + e.getMessage());
        }
        return new ApiRespWrapper<UserInfoResp>(-1, "Get user info failed.");
    }


}
