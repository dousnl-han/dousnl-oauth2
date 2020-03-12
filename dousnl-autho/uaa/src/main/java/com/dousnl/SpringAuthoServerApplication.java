package com.dousnl;

import com.alibaba.fastjson.JSON;
import com.dousnl.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 * OAuth2 ：主要用于搭建开放平台，client_id和client_secret相当于AppId和AppSecret。比如微信开放平台、支付宝开放平台，用户接入后，
 * 可得到username、password、appId、appSecret。
 * OAuth2之客户端模式：利用appId和appSecret向OAuth2-server认证授权服务器发送请求，获取Access Token，申请获取访问资源服务器的权限。
 * OAuth2之密码模式：利用appId和appSecre，再加上用户名和密码，向OAuth2-server认证授权服务器发送请求，获取Access Token，申请获取访问资源服务器的权限。
 * 因为，密码模式获取到的Access Token包含了用户信息，而客户端模式，并不包含，所以，资源服务器仅能粗粒度控制权限(这里的资源指整个项目)，而密码模式则能利用用户信息，
 * 认证成功后(此处的认证指的是对于Access Token校验是否拥有对于访问的资源服务器的权限)进一步利用Spring-Security框架的注解和OAuth2的
 * 注解进行接口甚至于方法级别的细粒度的控制。
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/7/25 17:56
 */
@SpringBootApplication
@RestController
public class SpringAuthoServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringAuthoServerApplication.class, args);
    }

    /**
     * This is a helper Java class that provides an alternative to creating a web.xml.
     * This will be invoked only when the application is deployed to a servlet container like Tomcat, JBoss etc.
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringAuthoServerApplication.class);
    }

    @RequestMapping("/say")
    public String say(){
        return JSON.toJSONString(new User("zhangsan","beijing.satation"));
    }

    @RequestMapping("/sayhi")
    public String sayHi(){
        return JSON.toJSONString(new User("zhangsan","beijing.satation"));
    }
}
