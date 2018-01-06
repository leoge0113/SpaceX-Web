package com.cainiao.enums;

public enum ResultEnum {

    // 数据库想操作异常
    DB_INSERT_RESULT_ERROR(99990001, "db insert error"),
    DB_UPDATE_RESULT_ERROR(99990002, "db update error"),
    DB_SELECTONE_IS_NULL(99990003, "db select return null"),

    // 系统异常
    INNER_ERROR(99980001, "系统错误"),
    TOKEN_IS_ILLICIT(99980002, "Token验证非法"),
    SESSION_IS_OUT_TIME(99980003, "会话超时"),

    // 用户相关异常
    INVALID_USER(1001001, "无效用户");
    private int state;
    private String msg;

    ResultEnum(int state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }
}
