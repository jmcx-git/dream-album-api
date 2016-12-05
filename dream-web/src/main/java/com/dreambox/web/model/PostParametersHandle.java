// Copyright 2013 appchina.com Inc. All Rights Reserved.

package com.dreambox.web.model;

import java.util.Map;

// import com.appchina.ios.core.crawler.model.EnterprisePlistParameter;

/**
 * @author luofei@appchina.com (Your Name Here)
 * 
 */
public interface PostParametersHandle<T> {
    public Map<String, Object> handle(T t);

}
