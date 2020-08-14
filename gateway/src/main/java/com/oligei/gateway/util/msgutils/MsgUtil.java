package com.oligei.gateway.util.msgutils;

/**
 * @ClassName: MsgUtil
 * @Description: MsgUtil
 * @Author: Cui Shaojie
 * @Date: 2020/8/14 15:37
 **/
public class MsgUtil {
    public static final int SUCCESS = 0;
    public static final int ERROR = -1;
    public static final int LOGIN_USER_ERROR = -100;
    public static final int NOT_LOGGED_IN_ERROR = -101;

    public static final String SUCCESS_MSG = "Success！";
    public static final String LOGIN_SUCCESS_MSG = "Login successfully！";
    public static final String LOGOUT_SUCCESS_MSG = "Log out successfully！";
    public static final String LOGOUT_ERR_MSG = "Log out error！";
    public static final String ERROR_MSG = "Error！";
    public static final String LOGIN_USER_ERROR_MSG = "Username or password is wrong！";
    public static final String LOGIN_USER_BANNED_MSG = "You have been banned！";
    public static final String NOT_LOGGED_IN_ERROR_MSG = "Please log in！";



//    template<class T>
//    public static Msg<T> makeMsg(MsgCode code, JSONObject data){
//        return new Msg<T>(code, data);
//    }
//
//    public static Msg makeMsg(MsgCode code, String msg, JSONObject data){
//        return new Msg(code, msg, data);
//    }
//
//    public static Msg makeMsg(MsgCode code){
//        return new Msg(code);
//    }
//
//    public static Msg makeMsg(MsgCode code, String msg){
//        return new Msg(code, msg);
//    }
//
//    public static Msg makeMsg(int status, String msg, JSONObject data){
//        return new Msg(status, msg, data);
//    }
//
//    public static Msg makeMsg(int status, String msg){
//        return new Msg(status, msg);
//    }
//
//    public static Msg makeMsg(int status, String msg, boolean data) {
//        return new Msg(status,msg,data);
//    }
}