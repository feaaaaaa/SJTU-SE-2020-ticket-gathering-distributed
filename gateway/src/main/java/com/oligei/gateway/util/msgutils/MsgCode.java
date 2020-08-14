package com.oligei.gateway.util.msgutils;

/**
 * @ClassName: MsgCode
 * @Description: MsgCode
 * @Author: Cui Shaojie
 * @Date: 2020/8/14 15:37
 **/
public enum  MsgCode {
    SUCCESS(MsgUtil.SUCCESS, MsgUtil.SUCCESS_MSG),
    ERROR(MsgUtil.ERROR,MsgUtil.ERROR_MSG),
    LOGIN_USER_ERROR(MsgUtil.LOGIN_USER_ERROR,MsgUtil.LOGIN_USER_ERROR_MSG),
    LOGIN_USER_BANNED(MsgUtil.LOGIN_USER_ERROR,MsgUtil.LOGIN_USER_BANNED_MSG),
    NOT_LOGGED_IN_ERROR(MsgUtil.NOT_LOGGED_IN_ERROR,MsgUtil.NOT_LOGGED_IN_ERROR_MSG);

    private int status;
    private String msg;

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    private MsgCode(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}

