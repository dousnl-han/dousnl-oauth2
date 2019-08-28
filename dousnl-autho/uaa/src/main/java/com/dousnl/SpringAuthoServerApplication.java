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
    public String sayHi(){
        return JSON.toJSONString(new User("zhangsan","beijing.satation"));
    }
}
