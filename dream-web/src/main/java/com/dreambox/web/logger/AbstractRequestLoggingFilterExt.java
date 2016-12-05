// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.dreambox.web.logger;

import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author luofei@refanqie.com (Your Name Here)
 */
public abstract class AbstractRequestLoggingFilterExt extends AbstractRequestLoggingFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, final HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (isExcludeUri(uri) || !isJsonRequest(uri)) {
            super.doFilterInternal(request, response, filterChain);
            afterRequest(request, response);
        } else {
            final HttpServletResponseLoggingWrapper httpServletResponseLoggingWrapper = new HttpServletResponseLoggingWrapper(response);
            super.doFilterInternal(request, httpServletResponseLoggingWrapper, filterChain);
            afterRequest(request, httpServletResponseLoggingWrapper);
        }
    }

    protected void afterRequest(HttpServletRequest request, HttpServletResponse response) {
        logResponse(request, response);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        //doNothing()
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        logRequest(request);
    }

    protected abstract void logRequest(HttpServletRequest request);

    protected abstract void logResponse(HttpServletRequest request, HttpServletResponse response);

    protected boolean isExcludeUri(String uri) {
        if (uri.startsWith("/appchina-ios-backend/admin/") || uri.startsWith("/admin/")) {
            return true;
        }
        return false;
    }

    protected boolean isJsonRequest(String uri) {
        return uri.endsWith(".json");
    }

}

