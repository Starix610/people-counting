package com.weiyun.peoplecounting.service;

import com.weiyun.peoplecounting.error.BusinessException;
import com.weiyun.peoplecounting.pojo.User;
import com.weiyun.peoplecounting.response.CommonReturnType;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {

    User login(String telephone, String code, String password, String realCode) throws BusinessException;

    List<User> getUsers();

    User getUser(String telephone);

    CommonReturnType sendSMSCode(String telephone, HttpSession session) throws BusinessException;

    CommonReturnType register(User user, String code, String realCode) throws BusinessException;

}
