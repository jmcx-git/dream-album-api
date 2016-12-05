// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.dto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD })
public @interface EnumHtmlSelectType {

    /**
     * @author luofei@appchina.com (Your Name Here)
     *
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.METHOD, ElementType.FIELD })
    public @interface KEY {

    }

    /**
     * @author luofei@appchina.com (Your Name Here)
     *
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.METHOD, ElementType.FIELD })
    public @interface VALUE {

    }

}
