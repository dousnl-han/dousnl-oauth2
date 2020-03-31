package com.dousnl.controller;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/9/10 13:54
 */
@Slf4j
@RestController
@RequestMapping("/reslover")
public class ResloverController {

    @GetMapping("/ex")
    public String postUser() {
        int a=1/0;
        return "1";
    }
}
