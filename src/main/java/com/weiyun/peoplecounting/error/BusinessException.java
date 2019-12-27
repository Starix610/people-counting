package com.weiyun.peoplecounting.error;

/**
 * 自定义业务异常类
 * 实现统一的错误信息接口
 * 实现里接口中的方法，方便Controller中的Exceptionhandler直接获取错误信息
 */
public class BusinessException extends Exception implements CommonError {

    private CommonError commonError;

    //直接接收EmBusinessError的传参用于构造业务异常
    public BusinessException(CommonError commonError) {
        super();
        this.commonError = commonError;
    }

    //接收自定义errorMsg的方式构造业务异常

    public BusinessException(CommonError commonError,String message) {
        super();
        this.commonError = commonError;
        this.commonError.setErrorMsg(message);
    }

    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return this.commonError.getErrorMsg();
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.commonError.setErrorMsg(errorMsg);
        return this;
    }
}
