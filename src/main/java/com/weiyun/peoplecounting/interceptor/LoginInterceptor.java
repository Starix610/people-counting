package com.weiyun.peoplecounting.interceptor;

import com.weiyun.peoplecounting.pojo.User;
import com.weiyun.peoplecounting.pojo.UserVO;
import com.weiyun.peoplecounting.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        UserVO userVO = (UserVO) request.getSession().getAttribute("user");
        if (userVO == null){
            //如果未登录，则先检查cookie中是否保存有用户信息
            String telephone = null;
            String password = null;
            //获得客户端携带的cooike
            Cookie[] cookies = request.getCookies();
            if (cookies!=null) {
                for (Cookie cookie : cookies) {
                    if ("cookie_telephone".equals(cookie.getName())) {
                        telephone = cookie.getValue();
                    }
                    if ("cookie_password".equals(cookie.getName())) {
                        password = cookie.getValue();
                    }
                }
            }
            //如果客户端cookie中保存有用户信息，则自动登录
            if (!StringUtils.isEmpty(telephone) && !StringUtils.isEmpty(password)) {
                User user1 = userService.login(telephone, null, password, null);
                userVO = new UserVO();
                BeanUtils.copyProperties(user1, userVO);
                request.getSession().setAttribute("user", userVO);
            }else {
                //通过一个接口返回未登录的json格式的错误信息
                request.getRequestDispatcher("/user/error/notlogined").forward(request, response);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
