package com.dousnl.reslover;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/9/10 15:28
 */
@Slf4j
@Component
public class InitBeanTest implements InitializingBean, DisposableBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(">>>>>>执行初始化方法..........");
        //TimeUnit.SECONDS.sleep(10);
    }

    @Override
    public void destroy() throws Exception {
        log.info(">>>>>>执行摧毁方法..........");
    }
}
