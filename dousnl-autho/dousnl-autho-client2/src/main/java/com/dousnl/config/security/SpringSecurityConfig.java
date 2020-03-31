package com.dousnl.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 开启jwt方式授权时注释掉此类，
 * 免登录，去服务器拿token即可
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/7/25 17:58
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        //基于内存来存储用户
        //auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
        //        .withUser("dousnl").password(new BCryptPasswordEncoder().encode("123")).roles("USER").and()
        //        .withUser("admin").password(new BCryptPasswordEncoder().encode("456")).roles("USER", "ADMIN");
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 访问"/","/home"路径的请求都允许
                .antMatchers("/", "/index.html").permitAll()
                // 而其他的请求都需要认证
                .anyRequest().authenticated().and()
                // 修改spring security默认登录页面
                .formLogin().and().logout().permitAll()
                .and().csrf().disable();
    }
}
