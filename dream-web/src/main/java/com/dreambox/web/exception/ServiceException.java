package com.dreambox.web.exception;

import java.sql.SQLException;

import org.apache.log4j.Logger;


public class ServiceException extends RuntimeException {
    private static final Logger log = Logger.getLogger(ServiceException.class);
    public static final int ERR_CODE_UNKNOWN_ERROR = 500;
    /**
     * 
     */
    private static final long serialVersionUID = -9204052688056220849L;
    public static final int ERR_CODE_PARAMETER_ILLEGAL = 1001;
    public static final int ERR_CODE_APPLICATION_ILLEGAL = 2001;
    public static final int ERR_CODE_APPLICATION_NOTFOUND = 2404;
    public static final int ERR_CODE_ROOT_APP_DATA_ILLEGAL = 20012;
    public static final int ERR_CODE_CATEGORY_PARENT_ID_ILLEGAL = 3001;
    public static final int ERR_CODE_CATEGORY_ID_ILLEGAL = 3001;
    public static final int ERR_CODE_SPIDER_TASK_REPORT_ERROR = 6001;
    private static final int ERR_CODE_SQL_ILLEGAL = 7001;
    private static final int ERR_CODE_SQL_DATA_ILLEGAL = 7002;
    private static final int ERR_CODE_CACHE_BUSY_ILLEGAL = 7003;
    private static final int ERR_CODE_CACHE_DATA_ILLEGAL = 7004;
    public static final int ERR_CODE_ACCOUNT_NOTFOUND = 8404;
    public static final int ERR_CODE_ACCOUNT_UNAUTHORIZED = 8401;
    public static final int ERR_CODE_ACCOUNT_ABERRANT = 8402;
    public static final int ERR_CODE_BUSSINES_ILLEGAL = 9001;
    private int errorCode;

    public ServiceException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceException(String message, Exception e) {
        this(ERR_CODE_UNKNOWN_ERROR, message + e.getMessage());
    }


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public static ServiceException getSQLException(String message) {
        return new ServiceException(ERR_CODE_SQL_ILLEGAL, message);
    }

    public static ServiceException getSQLException(SQLException e) {
        log.error("操作数据库失败.Errmsg:" + e.getMessage(), e);
        return new ServiceException(ERR_CODE_SQL_ILLEGAL, "数据操作失败.");
    }


    public static ServiceException getCacheBusyException() {
        return new ServiceException(ERR_CODE_CACHE_BUSY_ILLEGAL, "缓存服务器繁忙，请稍后再试.");
    }

    public static ServiceException getCacheDataException(String message) {
        return new ServiceException(ERR_CODE_CACHE_DATA_ILLEGAL, message);
    }


    public static ServiceException getDbDataExceptipn(String dataType) {
        return new ServiceException(ERR_CODE_SQL_DATA_ILLEGAL, "数据库中" + dataType + "错误.");
    }


    public static ServiceException getParameterException(String detail) {
        return new ServiceException(ERR_CODE_PARAMETER_ILLEGAL, "参数错误:" + detail);
    }

    public static ServiceException getAccountNotFound(int accountId) {
        return new ServiceException(ERR_CODE_ACCOUNT_NOTFOUND, "未找到账号Id:" + accountId + "所对应的账号信息!");
    }

    public static ServiceException getAccountNotFound(String email) {
        return new ServiceException(ERR_CODE_ACCOUNT_NOTFOUND, "未找到账号Email:" + email + "所对应的账号信息!");
    }

    public static ServiceException getAppNotFound(int rootId) {
        return new ServiceException(ERR_CODE_APPLICATION_NOTFOUND, "未找到应用根Id" + rootId + "对应的应用信息.");
    }

    public static ServiceException getInternalException(String message) {
        return new ServiceException(ERR_CODE_UNKNOWN_ERROR, message);
    }

    public static ServiceException getApplicationIllegalException(String message) {
        return new ServiceException(ERR_CODE_APPLICATION_ILLEGAL, message);
    }

    public static ServiceException getBussinesError(String message) {
        return new ServiceException(ERR_CODE_BUSSINES_ILLEGAL, message);
    }

    public static ServiceException getAccountUnauthorized(String message) {
        return new ServiceException(ERR_CODE_ACCOUNT_UNAUTHORIZED, message);
    }

    public static ServiceException getAccountAberrant(String message) {
        return new ServiceException(ERR_CODE_ACCOUNT_ABERRANT, message);
    }
}
