package com.dousnl.reslover;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/9/10 13:50
 */
@Deprecated
@Slf4j
//@Component
public class WebExceptionReslover implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.error("异常信息：{}，异常堆栈信息：{}", e.getMessage(), e);
        return null;
    }
}
