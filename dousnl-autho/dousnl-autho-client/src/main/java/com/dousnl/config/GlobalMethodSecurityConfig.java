package com.dousnl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/8/26 18:21
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启方法级别安全验证
public class GlobalMethodSecurityConfig {
}
