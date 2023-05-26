package com.zlhhh.springboot.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zlhhh.springboot.common.Constants;
import com.zlhhh.springboot.entity.User;
import com.zlhhh.springboot.exception.ServiceException;
import com.zlhhh.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        if (!(handler instanceof HandlerMethod)) {  // 如果不是映射到方法直接通过 在方法上验证
            return true;
        }
        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(Constants.CODE_401, "无token，请重新登录");
        }
        // 获取token中的user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);  //userid
        } catch (JWTDecodeException e) {
            throw new ServiceException(Constants.CODE_401, "token验证失败");
        }
        // 根据token中的userid查询数据库
        User user = userService.getById(userId);
        if (user == null) {
            throw new ServiceException(Constants.CODE_401, "用户不存在");
        }
        // 用户密码加签验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);  // 验证token
        } catch (JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_401, "token验证失败");
        }
        return true;
    }
}
