package com.oligei.ticketgathering.util.msgutils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


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




    public static Msg makeMsg(MsgCode code, JSONObject data){
        return new Msg(code, data);
    }

    public static Msg<JSONObject> makeMsg(MsgCode code, String msg, JSONObject data){
        return new Msg<JSONObject>(code, msg, data);
    }

    public static Msg<JSONObject> makeMsg(MsgCode code){
        return new Msg<JSONObject>(code);
    }

    public static Msg<JSONObject> makeMsg(MsgCode code, String msg){
        return new Msg<JSONObject>(code, msg);
    }

    public static Msg<JSONObject> makeMsg(int status, String msg, JSONObject data){
        return new Msg<JSONObject>(status, msg, data);
    }

    public static Msg<JSONObject> makeMsg(int status, String msg){
        return new Msg<JSONObject>(status, msg);
    }

    public static Msg<JSONArray> makeMsg(MsgCode code, String msg, JSONArray data){
        return new Msg<JSONArray>(code, msg, data);
    }
}
