// Copyright 2016 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core;

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
public @interface DbField {

    /**
     * @author luofei@appchina.com (Your Name Here)
     *
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.METHOD, ElementType.FIELD })
    public @interface ZERO_ENABLE {

    }

    /**
     * @author luofei@appchina.com (Your Name Here)
     *
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.METHOD, ElementType.FIELD })
    public @interface UNSIGNED_INT {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.METHOD, ElementType.FIELD })
    public @interface SELECT_ALL_KEY {
    }
}
