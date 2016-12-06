// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dreambox.core.dto.album;

import com.dreambox.core.DbStatus;

/**
 * @author git@jmcxclub.com create date: Dec 6, 2016
 *
 */
public class UserInfo extends DbStatus {
    private int id;
    private String name;// refer from wx info.nickName
    private String avator;// refer from wx info.avatorUrl
    private String gender;// refer from wx info.gender[性别 0:未知、1:男、2:女]
    private String openId;// refer from wx info.openId[用户的标识，只对当前小程序唯一]
    private String unionId;// refer from wx info.unionId;[多平台唯一标识]

}
