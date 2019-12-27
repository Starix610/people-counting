package com.weiyun.peoplecounting.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weiyun.peoplecounting.error.BusinessException;
import com.weiyun.peoplecounting.error.EmBusinessError;
import com.weiyun.peoplecounting.mapper.TeacherMapper;
import com.weiyun.peoplecounting.mapper.UserMapper;
import com.weiyun.peoplecounting.miaodiyun.IndustrySMS;
import com.weiyun.peoplecounting.pojo.User;
import com.weiyun.peoplecounting.response.CommonReturnType;
import com.weiyun.peoplecounting.service.UserService;
import com.weiyun.peoplecounting.utils.RandomNumUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class UserServiceImpl implements UserService {

    private final static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public CommonReturnType sendSMSCode(String telephone, HttpSession session) throws BusinessException {
        // 生成6位随机数字验证码
        String randomNum = RandomNumUtil.getRandomNum();
        String result = IndustrySMS.sendCode(telephone, randomNum, 30);
        JSONObject jsonObject = JSON.parseObject(result);
        String respCode = jsonObject.get("respCode").toString();
        String respDesc = jsonObject.get("respDesc").toString();
        if ("00000".equals(respCode)){
            //验证码存到session
            session.setAttribute(telephone+"_code", randomNum);
            return CommonReturnType.create("验证码已发送到您的手机上，请注意查收！");
        }else if ("00025".equals(respCode)){
            throw new BusinessException(EmBusinessError.SMS_CODE_SEND_FAIL, "手机格式不对！");
        }else if ("00141".equals(respCode)){
            throw new BusinessException(EmBusinessError.SMS_CODE_SEND_FAIL, "一小时内发送给单个手机次数超过限制(4次)");
        }else {
            logger.warn("短信发送失败,错误码:"+respCode+",描述："+respDesc);
            throw new BusinessException(EmBusinessError.SMS_CODE_SEND_FAIL, "短信发送失败,请稍后重试！错误码："+respCode);
        }
    }

    @Override
    public CommonReturnType register(User user, String code, String realCode) throws BusinessException {

        if (!code.equals(realCode)){
            throw new BusinessException(EmBusinessError.WRONG_SMS_CODE);
        }
        String telephone = user.getTelephone();
        User user1 = userMapper.selectByTelephone(telephone);
        if (user1 != null){
            throw new BusinessException(EmBusinessError.TELEPHONE_ALREADY_EXIST); //手机号码已被注册
        }
        userMapper.insert(user);
        return CommonReturnType.create("注册成功");
    }


    @Override
    public User login(String telephone, String code, String password, String realCode) throws BusinessException {

        //判断验证类型(验证码/密码)
        if (!StringUtils.isEmpty(code)){
            if (!StringUtils.isEmpty(realCode) && code.equals(realCode)){
                User user = getUser(telephone);
                return user;
            } else {
                throw new BusinessException(EmBusinessError.WRONG_SMS_CODE, "验证码不正确");
            }
        }
        if (!StringUtils.isEmpty(password)){
            User user = getUser(telephone);
            if (user != null && StringUtils.isEmpty(user.getPassword())){
                throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL, "该账号尚未设置登录密码");
            }else if (user == null || !password.equals(user.getPassword())){
                throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL, "用户名或密码错误");
            }else {
                return user;
            }
        }
        throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL, "登录失败，请稍后重试");
    }


    @Override
    public User getUser(String telephone) {
        User user = userMapper.selectByTelephone(telephone);
        return user;
    }

    @Override
    public List<User> getUsers() {
        return userMapper.selectAll();
    }
}
