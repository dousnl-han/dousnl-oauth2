package com.dousnl.controller;

import com.alibaba.fastjson.JSON;
import com.dousnl.domain.User;
import com.dousnl.domain.UserLoginDTO;
import com.dousnl.mapper.UserMapper;
import com.dousnl.service.UserServiceDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/8/26 18:40
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserServiceDetail userServiceDetail;
    @Autowired
    private UserMapper userMapper;

    private static final SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public User postUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userServiceDetail.insertUser(username, password);
    }

    @PostMapping("/login")
    public UserLoginDTO login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userServiceDetail.login(username, password);
    }

    @GetMapping("/getuser")
    public User login(@RequestParam("id") Integer id) {
        User user = userServiceDetail.getUser(id);
        return user;
    }

    @GetMapping("/update")
    public int update(@RequestParam("id") Integer id) {
        User u=new User();u.setId(id);u.setUsername("count");
        int count = userMapper.updateByPrimaryKey(u);
        System.out.println("count:"+count);
        return count;
    }
}
