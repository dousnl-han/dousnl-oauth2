package com.dousnl.filter;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/12/12 13:49
 */
public class JwtAuthFilter extends GenericFilterBean {

    private static final String HEADER_STRING = "Authorization";// 存放Token的Header Key
    private static final String TOKEN_PREFIX = "ZD ";        // Token前缀

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        String token = request.getHeader(HEADER_STRING);

        //没有token
//        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
//            try {
//                System.err.println(">>>>>>>>>>>>请求路径:" + request.getRequestURI());
//                throw new Exception("没有权限....");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void destroy() {

    }
}
