package com.weiyun.peoplecounting.controller;

import com.weiyun.peoplecounting.error.BusinessException;
import com.weiyun.peoplecounting.error.EmBusinessError;
import com.weiyun.peoplecounting.response.CommonReturnType;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用基类
 * 带有业务异常处理方法
 */


//@ControllerAdvice  //该注解可以直接对全局的Controller进行异常处理，不加默认则默认只能在当前类处理
public class BaseController {

    private static final Logger logger = Logger.getLogger(BaseController.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception e){
        Map<String,Object> responseData = new HashMap<>();
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            responseData.put("errorCode", businessException.getErrorCode());
            responseData.put("errorMsg", businessException.getErrorMsg());

        }else {
            /**
             * 不能直接返回枚举类中的实例，因为responbody默认序列化机制会直接把enum类型实例直接变成UNKNOWN_ERROR之类的，不能返回正确数据
             * 因此使用枚举类中的方法获取信息并放到
             */
            responseData.put("errorCode", EmBusinessError.UNKNOWN_ERROR.getErrorCode());
            responseData.put("errorMsg", EmBusinessError.UNKNOWN_ERROR.getErrorMsg());
            logger.error(e.toString(),e);
        }
        return CommonReturnType.create("fail",responseData);

    }

}
