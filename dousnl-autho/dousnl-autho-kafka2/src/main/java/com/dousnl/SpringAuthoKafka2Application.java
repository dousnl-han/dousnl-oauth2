package com.dousnl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/7/25 17:56
 */
@SpringBootApplication
@RestController
public class SpringAuthoKafka2Application extends SpringBootServletInitializer implements ServletContextInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringAuthoKafka2Application.class, args);
    }
    @RequestMapping("/say")
    public String sayHi(){
        return "sak";
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.getSessionCookieConfig().setName("client-session");
    }

    /**
     * This is a helper Java class that provides an alternative to creating a web.xml.
     * This will be invoked only when the application is deployed to a servlet container like Tomcat, JBoss etc.
     *
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringAuthoKafka2Application.class);
    }

}
