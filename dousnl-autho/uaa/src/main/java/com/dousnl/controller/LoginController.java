package com.dousnl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2020/3/31 14:14
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String home() {
        System.out.println(">>>>>>>>>>>>>>>>//////index");
        return "login";
    }
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
