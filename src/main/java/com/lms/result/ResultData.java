package com.lms.result;


import com.lms.contants.HttpCode;

import java.util.HashMap;
import java.util.Objects;

public class ResultData extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";

    /**
     * 返回内容
     */
    public static final String MSG_TAG = "msg";

    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";


    public static final  String ERROR_INFO = "error_info";
    public ResultData() {

    }

    public ResultData(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }


    public ResultData(int code, String msg, Object data) {

        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (!Objects.isNull(data)) {
            super.put(DATA_TAG, data);
        }
    }

    //============请求成功================================
    public static ResultData success() {
        return ResultData.success(HttpCode.SUCCESS);
    }


    public static ResultData success(String msg) {
        return ResultData.success(msg, null);
    }
    public static ResultData success(Object data){
        return ResultData.success(HttpCode.SUCCESS.getMessage(),data);
    }

    public static ResultData success(String msg, Object data) {
       return ResultData.success(HttpCode.SUCCESS.getCode(),msg,data);
    }
    public static ResultData success(int code,String msg,Object data){
        return new ResultData(code,msg,data);
    }

    //=============请求失败==============================
    public static ResultData error() {
        return ResultData.error(HttpCode.SYSTEM_ERROR.getMessage());
    }
    public static ResultData error(Object data){
        return ResultData.error(HttpCode.SYSTEM_ERROR.getMessage(),data);
    }
    public static ResultData error(String msg) {
        return ResultData.error(msg, null);
    }

    public static ResultData error(String msg, Object data) {
     return ResultData.error(HttpCode.OPERATION_ERROR,msg,data);
    }

    public static ResultData error(HttpCode httpCode, String msg, Object data){
        ResultData resultData = new ResultData(httpCode.getCode(), msg);
        if(!Objects.isNull(data)){
            resultData.put(ERROR_INFO,data);
        }
        return resultData;
    }

    @Override
    public ResultData put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
