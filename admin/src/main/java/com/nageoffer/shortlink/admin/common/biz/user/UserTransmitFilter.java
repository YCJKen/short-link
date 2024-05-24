package com.nageoffer.shortlink.admin.common.biz.user;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.util.Objects;

/**
 * 用户信息传输过滤器
 */
@RequiredArgsConstructor
public class UserTransmitFilter implements Filter {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();
        // 对请求接口进行过滤拦截，如果请求接口为登录接口则直接放行
        // 任何请求，使用过滤器进行过滤，需要从请求头（header）中获取username和token，然后根据username和token在缓存中获取对应用户信息
        // 并将用户信息存入UserContext中， 如果token过期了，redis会自动删除，那么此时stringRedisTemplate中是没有有效token供获取的
        // 即得到的用户信息为空，用户上下文UserContext中的userInfoDTO为空
        if (!Objects.equals(requestURI, "/api/short-link/v1/user/login")) {
            String username = httpServletRequest.getHeader("username");    //从请求中获取username
            String token = httpServletRequest.getHeader("token");    //从请求中获取token
            Object userInfoJsonStr = stringRedisTemplate.opsForHash().get("login_" + username, token);
            if (userInfoJsonStr != null) {
                UserInfoDTO userInfoDTO = JSON.parseObject(userInfoJsonStr.toString(), UserInfoDTO.class);
                UserContext.setUser(userInfoDTO);
            }
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserContext.removeUser();
        }
    }
}