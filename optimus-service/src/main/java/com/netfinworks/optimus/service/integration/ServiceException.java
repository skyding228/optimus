/*
 * Copyright 2013 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.optimus.service.integration;

/**
 * <p></p>
 *
 * @author 
 * @version 
 */
public class ServiceException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5799387327727362405L;
	private ErrorKind errorKind;
    private String errorCode;
    private String errorMessage;


    public ErrorKind getErrorKind() {
        return errorKind;
    }

    public void setErrorKind(ErrorKind errorKind) {
        this.errorKind = errorKind;
    }
    public ServiceException(ErrorKind errorKind, String message){
        this(errorKind.getCode(),errorKind.getMsg()+":"+message);
    }
    public ServiceException(ErrorKind errorKind, String message, Throwable t){
        this(errorKind.getCode(),errorKind.getMsg()+":"+message, t);
    }
    
    public ServiceException(ErrorKind errorKind, Throwable t){
        this(errorKind.getCode(),errorKind.getMsg(), t);
    }
    public ServiceException(ErrorKind errorKind){
        this(errorKind.getCode(),errorKind.getMsg());
    }
    public ServiceException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    public ServiceException(String errorCode, String errorMessage, Throwable t) {
        super(errorMessage,t);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
