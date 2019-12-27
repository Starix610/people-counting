package com.weiyun.peoplecounting.controller;

import com.weiyun.peoplecounting.error.BusinessException;
import com.weiyun.peoplecounting.error.EmBusinessError;
import com.weiyun.peoplecounting.pojo.LoginParam;
import com.weiyun.peoplecounting.pojo.User;
import com.weiyun.peoplecounting.pojo.UserVO;
import com.weiyun.peoplecounting.response.CommonReturnType;
import com.weiyun.peoplecounting.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api(description = "用户模块接口")
public class UserController extends BaseController {


    @Autowired
    private UserService userService;

    @ApiOperation(value = "发送短信验证码", notes = "向用户发送短信验证码")
    @GetMapping("/sendcode")
    public CommonReturnType sendCode(String telephone,HttpSession session) throws BusinessException {

        if (StringUtils.isEmpty(telephone)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION, "缺少telephone参数");
        }
        CommonReturnType result = userService.sendSMSCode(telephone,session);
        return result;
    }


    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping("/register")
    public CommonReturnType register(@RequestBody User user,String code,HttpSession session) throws BusinessException {
        if (StringUtils.isEmpty(user.getTelephone())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION, "telephone不能为空");
        }else if (StringUtils.isEmpty(user.getPassword())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION, "password不能为空");
        }else if (StringUtils.isEmpty(code)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION,"验证码不能为空");
        }
        String realCode = (String) session.getAttribute(user.getTelephone()+"_code");
        CommonReturnType result = userService.register(user,code,realCode);
        session.removeAttribute(user.getTelephone()+"_code"); //注册完成清除掉session中的验证码
        return result;
    }

    @ApiOperation(value = "用户登录", notes = "用户登录，使用短信验证码或者密码")
    @PostMapping(value = "/login")
    public CommonReturnType login(@RequestBody LoginParam json, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws BusinessException {

        String telephone = json.getTelephone();
        String code = json.getCode();
        String password = json.getPassword();

        if (StringUtils.isEmpty(telephone)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION, "缺少telephone参数");
        }
        if (StringUtils.isEmpty(code) && StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION, "缺少参数");
        }
        String realCode = (String) session.getAttribute(telephone+"_code");
        User user = userService.login(telephone, code, password, realCode);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        session.setAttribute("user", userVO);
        session.removeAttribute(telephone+"_code");

        //向客户端写cookie
        Cookie cookie_telephone = new Cookie("cookie_telephone", telephone);
        Cookie cookie_password = new Cookie("cookie_password", password);
        //持久化cookie到客户端7天
        cookie_telephone.setMaxAge(60*60*24*7);
        cookie_password.setMaxAge(60*60*24*7);
        cookie_telephone.setPath("/");
        cookie_password.setPath("/");
        response.addCookie(cookie_telephone);
        response.addCookie(cookie_password);

        HashMap<String,Object> map = new HashMap<>();
        map.put("msg", "登录成功");
        map.put("user", userVO);
        return CommonReturnType.create(map);
    }

    @ApiOperation(value = "用户注销")
    @GetMapping("/logout")
    public CommonReturnType logout(HttpSession session, HttpServletResponse response) throws BusinessException {
        session.invalidate();
        //删除客户端账号密码cookie
        Cookie cookie_telephone = new Cookie("cookie_telephone", "");
        cookie_telephone.setMaxAge(0);
        Cookie cookie_password = new Cookie("cookie_password", "");
        cookie_password.setMaxAge(0);
        cookie_telephone.setPath("/");
        cookie_password.setPath("/");
        response.addCookie(cookie_telephone);
        response.addCookie(cookie_password);
        return CommonReturnType.create("注销成功");
    }



    @ApiOperation(value = "获取用户详息", notes = "获取用户详细信息")
    @GetMapping("/{telephone}")
    public CommonReturnType getUser(@PathVariable("telephone") String telephone) throws BusinessException {
        if (StringUtils.isEmpty(telephone)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION, "缺少telephone参数");
        }
        User user = userService.getUser(telephone);
        if (user == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return CommonReturnType.create(userVO);
    }

    @ApiOperation(value = "获取用户列表", notes = "获取所有用户信息",httpMethod="GET")
    @GetMapping("/list")
    public CommonReturnType test(){
        List<User> list = userService.getUsers();
        List<UserVO> list1 = new ArrayList<>();
        for (User user : list) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            list1.add(userVO);
        }
        return CommonReturnType.create(list1);
    }

    //用于登录拦截器返回未登录信息(不用手动在拦截器中转换json)
    @ApiIgnore
    @GetMapping("/error/notlogined")
    @ResponseBody
    public CommonReturnType userNotLogin() throws BusinessException {
        throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
    }

}
