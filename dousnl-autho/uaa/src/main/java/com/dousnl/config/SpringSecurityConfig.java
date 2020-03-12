package com.dousnl.config;

import com.dousnl.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/7/25 17:58
 */

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * WebSecurity构建目标是整个Spring Security安全过滤器FilterChainProxy,
     * 而HttpSecurity的构建目标仅仅是FilterChainProxy中的一个SecurityFilterChain。
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                // 访问"/","/home"路径的请求都允许
                .antMatchers("/say", "/home", "/staff", "/staff/*").permitAll()
                //此处仅仅是放行了SecurityFilterChain，整个Spring Security安全过滤器FilterChainProxy
                //并没有全部放行，JwtAuthFilter依然有效，针对没有自定义过滤器时，此处放行可行，如果有自定义过滤器
                //此处放行，并没有彻底放行，要彻底放行，需要在 configure(WebSecurity web)方法中放行
                .antMatchers("/sayhi").permitAll()
                // 而其他的请求都需要认证
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthFilter(),UsernamePasswordAuthenticationFilter.class)
                .httpBasic()
                .and()
                // 修改spring security默认登录页面
                .formLogin().loginPage("/login").permitAll().and().logout().permitAll();
    }

    /**
     * WebSecurity构建目标是整个Spring Security安全过滤器FilterChainProxy,
     * 而HttpSecurity的构建目标仅仅是FilterChainProxy中的一个SecurityFilterChain。
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        /**
         * 此处增加、springSecurity  FilterChainProxy过滤器链放行
         * 放行、/sayhi请求
         * 针对JwtAuthFilter
         * JwtAuthFilter未交由Spring管理，属于springSecurity自定义过滤器
         */
        web.ignoring().antMatchers("/sayhi");
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //@Bean
    public static PasswordEncoder passwordEncoder(){
        DelegatingPasswordEncoder delegatingPasswordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //设置defaultPasswordEncoderForMatches为NoOpPasswordEncoder
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());
        return delegatingPasswordEncoder;

    }


}
