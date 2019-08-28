package com.aiways.recovery.controller;

import com.aiways.recovery.domain.TUser;
import com.aiways.recovery.mapper.TUserMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/8/20 17:42
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private TUserMapper tUserMapper;

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "用户管理", tags = {"用户管理"}, notes = "用户管理")
    public TUser hello(@PathVariable Integer id){
        return tUserMapper.selectByPrimaryKey(id);
    }

}
