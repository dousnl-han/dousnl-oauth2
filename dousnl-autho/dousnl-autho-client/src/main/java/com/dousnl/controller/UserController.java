package com.dousnl.controller;

import com.dousnl.domain.User;
import com.dousnl.domain.UserLoginDTO;
import com.dousnl.service.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/8/26 18:40
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserServiceDetail userServiceDetail;

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
        return userServiceDetail.getUser(id);
    }
}
