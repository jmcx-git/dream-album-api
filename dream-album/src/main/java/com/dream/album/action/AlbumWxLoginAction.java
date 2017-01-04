package com.dream.album.action;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dream.album.model.UserFullInfo;
import com.dream.album.model.UserInfoResp;
import com.dream.album.model.UserOpenIdInfo;
import com.dream.album.service.UserOpenIdInfoService;
import com.dream.album.util.AES;
import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.service.album.UserInfoService;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.utils.GsonUtils;

/**
 * 
 * @author yhx
 *
 */
@Controller
@RequestMapping("/album/user/*")
public class AlbumWxLoginAction {
    public static Logger log = Logger.getLogger(AlbumWxLoginAction.class);
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserOpenIdInfoService userOpenIdInfoService;

    @RequestMapping("/getSession.json")
    @ResponseBody
    public String getUser3rdSesseion(String appId, String code) {
        UserOpenIdInfo info = userOpenIdInfoService.getUser3rdSesseion(appId, code);
        return info.getOpenid();
    }

    @RequestMapping("/getUserInfo.json")
    @ResponseBody
    public ApiRespWrapper<UserInfoResp> getUserInfo(String openId, String encryptedData, String iv, String appId) {
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

}
