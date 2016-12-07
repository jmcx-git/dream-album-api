package com.dream.album.action;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.JedisPool;

import com.dream.album.model.UserFullInfo;
import com.dream.album.model.UserOpenIdInfo;
import com.dream.album.service.UserOpenIdInfoService;
import com.dream.album.util.AES;
import com.dreambox.core.dto.album.UserInfo;
import com.dreambox.core.service.album.UserInfoService;
import com.dreambox.core.utils.RedisCacheUtils;
import com.dreambox.web.utils.GsonUtils;

/**
 * 
 * @author yhx
 *
 */
@Controller
@RequestMapping("/dream/user/login/*")
public class WxLoginAction {

    public static Logger log = Logger.getLogger(WxLoginAction.class);
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserOpenIdInfoService userOpenIdInfoService;
    @Resource(name = "jmcx-wx-redisdbpool")
    private JedisPool jedisDbPool;

    @RequestMapping("/getSession.json")
    @ResponseBody
    public String getUser3rdSesseion(String code) {
        UserOpenIdInfo info = userOpenIdInfoService.getUser3rdSesseion(code);
        // 放入缓存
        String sessionKey = UUID.randomUUID().toString().replace("-", "");
        RedisCacheUtils.set(sessionKey, info.getSession_key() + "#" + info.getOpenid(), jedisDbPool);
        System.out.println(info.getSession_key() + "#" + info.getOpenid());
        return sessionKey;
    }

    @RequestMapping("/getUserInfo.json")
    @ResponseBody
    public String getUserInfo(String threeSessionKey, String encryptedData, String iv) {
        String userInfo = null;
        String secret = RedisCacheUtils.get(threeSessionKey, jedisDbPool);
        if (StringUtils.isEmpty(secret)) {
            return null;
        }
        try {
            AES aes = new AES();
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData),
                    Base64.decodeBase64(secret.split("#")[0]), Base64.decodeBase64(iv));
            if (null != resultByte && resultByte.length > 0) {
                userInfo = new String(resultByte, "UTF-8");
            }
        } catch (Exception e) {
            log.error("decode userinfo failed." + e.getMessage());
        }
        if (!StringUtils.isEmpty(userInfo)) {
            try {
                UserFullInfo info = GsonUtils.convert(userInfo, UserFullInfo.class);
                UserInfo g = new UserInfo();
                BeanUtils.copyProperties(info, g);
                g.setAppid(info.getWatermark().getAppid());
                int id = userInfoService.addDataAndReturnId(g);
                return String.valueOf(id);
            } catch (Exception e) {
                log.error("parse userinfo failed." + e.getMessage());
            }
        }
        return null;
    }
}
