package com.weiyun.peoplecounting.error;


/**
 * 错误信息枚举类，实现了通用的CommError接口
 */
public enum EmBusinessError implements CommonError {

    //通用错误类型10000，错误信息可以具体定制
    PARAMETER_VALIDATION(10001,"参数不合法"),

    UNKNOWN_ERROR(10002,"未知错误"),

    USER_NOT_EXIST(20001,"用户不存在"),

    SMS_CODE_SEND_FAIL(20002,"短信发送失败"),

    USER_LOGIN_FAIL(20003,"登陆失败"),

    USER_NOT_LOGIN(20004,"用户未登录"),

    TELEPHONE_ALREADY_EXIST(20005,"该手机号码已被注册过"),

    EMAIL_ALREADY_EXIST(20006,"该邮箱已被注册过"),

    WRONG_SMS_CODE(20007,"验证码不正确"),

    TEACHER_INFO_ALREADY_ADDED(20008,"教师信息已经被另一账号绑定"),

    TEACHER_BIND_DUPLICATE(20009,"不能重复绑定"),

    TEACHER_INFO_NOT_BIND(200010,"未绑定教师信息"),

    COURSE_INFO_ALREADY_EXIST(20011,"课程上课时间冲突"),

    NO_COURSE_ADDED(20012,"未添加过任何课程信息"),

    NO_ATTENDANCE_ADDED(20013,"未上传过识别数据"),

    NO_DATA(20014,"没有数据");


    //定义其他错误信息...
    ;

    private int errorCode;
    private String errorMsg;

    EmBusinessError(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }
}
