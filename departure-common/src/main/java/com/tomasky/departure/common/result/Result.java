package com.tomasky.departure.common.result;

import java.io.Serializable;

/**
 * @author: sam
 * @Description 统一返回结果
 */
public class Result implements Serializable {

    private static final long serialVersionUID = -2742391645138232450L;

    /**
     * 结果状态码
     */
    private String status;

    /**
     * 成功标志
     */
    private boolean success;

    /**
     * 业务处理返回数据
     */
    private Object data;

    /**
     * 返回消息
     */
    private String message;

    public Result() {

    }

    public Result(ResultCodeEnum resultCode) {
        this.status = resultCode.getStatus();
        this.message = resultCode.getMessage();
        if (resultCode == ResultCodeEnum.SUCCESS) {
            this.success = true;
        }
    }

    public Result(ResultCodeEnum resultCode, Object data) {
        this.status = resultCode.getStatus();
        this.message = resultCode.getMessage();
        this.data = data;
        if (resultCode == ResultCodeEnum.SUCCESS) {
            this.success = true;
        }
    }

    /**
     * 附加返回错误信息
     *
     * @param resultCode
     * @param dataOrErrorMsg
     */
    public Result(ResultCodeEnum resultCode, String dataOrErrorMsg) {
        this.status = resultCode.getStatus();
        if (resultCode == ResultCodeEnum.SUCCESS) {
            this.success = true;
            this.data = dataOrErrorMsg;
        } else {
            this.message = resultCode.getMessage() + ": " + (null != dataOrErrorMsg ? dataOrErrorMsg : "");
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean isSuccess) {
        this.success = isSuccess;
    }

}
