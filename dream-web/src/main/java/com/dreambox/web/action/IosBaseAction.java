package com.dreambox.web.action;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.utils.IPUtil;


public class IosBaseAction {
    private static final Logger log = Logger.getLogger(IosBaseAction.class);

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ApiRespWrapper<Object> handleServiceException(ServiceException e, HttpServletRequest request) {
        log.error("Handle action failed.Ip:" + IPUtil.getClientIP(request), e);
        return new ApiRespWrapper<Object>(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiRespWrapper<Object> handleException(Exception e, HttpServletRequest request) {
        if (e.getClass().getName().contains("ClientAbortException")) {
            log.error("Handle action " + e.getClass().getName(), e);
            return new ApiRespWrapper<Object>(ServiceException.ERR_CODE_UNKNOWN_ERROR, e.getMessage());
        }
        String urlMapping = request.getRequestURL().toString();
        log.error("Handle action " + urlMapping + " failed.Ip:" + IPUtil.getClientIP(request) + ",Paras:"
                + mapToString(request.getParameterMap()), e);
        return new ApiRespWrapper<Object>(ServiceException.ERR_CODE_UNKNOWN_ERROR, e.getMessage());
    }

    private static String mapToString(Map<String, ?> map) {
        String ret = "{";
        boolean start = true;
        for (Entry<String, ?> entry : map.entrySet()) {
            if (start) {
                ret += entry.getKey() + "=";
                start = false;
            } else {
                ret += ", " + entry.getKey() + "=";
            }
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            if (value instanceof Object[] || value.getClass().isArray()) {
                ret += Arrays.toString((Object[]) value);
            } else {
                ret += Objects.toString(value);
            }
        }
        return ret + "}";
    }

    protected ApiRespWrapper<Boolean> returnBooleanResp(String errMsg) {
        if (StringUtils.isEmpty(errMsg)) {
            return new ApiRespWrapper<Boolean>(true);
        } else {
            return new ApiRespWrapper<Boolean>(-1, errMsg, false);
        }
    }

}
