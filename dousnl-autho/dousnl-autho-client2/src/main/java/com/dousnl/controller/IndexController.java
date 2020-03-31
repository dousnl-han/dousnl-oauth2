package com.dousnl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2020/3/31 12:50
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String home() {
        return "index";
    }
}
