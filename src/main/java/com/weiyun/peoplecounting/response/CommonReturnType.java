package com.weiyun.peoplecounting.response;

import com.weiyun.peoplecounting.error.CommonError;

import java.util.HashMap;
import java.util.Map;

public class CommonReturnType {

    // success or fail
    private String status;

    private Object data;



    public static CommonReturnType create(Object data){
        return CommonReturnType.create("success",data);
    }

    public static CommonReturnType create(String status, Object data) {
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.status = status;
        commonReturnType.data = data;
        return commonReturnType;
    }
    public static CommonReturnType error(CommonError commonError){
        Map<String,Object> responseData = new HashMap<>();
        responseData.put("errorCode", commonError.getErrorCode());
        responseData.put("errorMsg", commonError.getErrorMsg());
        return CommonReturnType.create("fail",responseData);
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
