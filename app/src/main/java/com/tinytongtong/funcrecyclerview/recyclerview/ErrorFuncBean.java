package com.tinytongtong.funcrecyclerview.recyclerview;

/**
 * @Description:
 * @Author wangjianzhou@qding.me
 * @Date 2020/5/25 8:15 AM
 * @Version
 */
public class ErrorFuncBean implements FuncBaseBean {
    public static final int DEFAULT_ERROR = 1;
    public static final int NET_ERROR = 2;

    private int errorType = 1;

    @Override
    public int getViewType() {
        return FuncConst.ERROR_DATA_TYPE;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }
}
