package com.dousnl.service;

import com.alibaba.fastjson.JSON;
import com.dousnl.domain.JWT;
import com.dousnl.domain.User;
import com.dousnl.domain.UserLoginDTO;
import com.dousnl.mapper.UserMapper;
import com.dousnl.util.BPwdEncoderUtil;
import com.dousnl.util.HttpClentUtils;
import com.dousnl.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/8/26 18:22
 */
@Slf4j
@Service
public class UserServiceDetail {

    @Autowired
    private UserMapper userMapper;

    public User insertUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(BPwdEncoderUtil.BCryptPassword(password));
        user.setRoleId(0);
        int count = userMapper.insertSelective(user);
        return count > 0 ? user : null;
    }

    public UserLoginDTO login(String username, String password){
        User user=userMapper.selectByUser(new User(username));
        if(user==null){
            throw new RuntimeException("用户不存在");
        }
        if(!BPwdEncoderUtil.matches(password, user.getPassword())){
            throw new RuntimeException("用户密码不对");
        }
        JWT jwt = execPost(username,password);
        if(jwt==null){
            throw new RuntimeException("用户Token有问题");
        }
        UserLoginDTO dto=new UserLoginDTO();
        dto.setUser(user);
        dto.setJwt(jwt);

        return dto;
    }

    public JWT execPost(String username, String password){
        //dXNlci1zZXJ2aWNlOjEyMzQ1Ng== 是 user-service:123456的 base64编码
        //JWT jwt=client.getToken("Basic dXNlci1zZXJ2aWNlOjEyMzQ1Ng==", "password", username, password);
        String url="http://localhost:8080/oauth/token";
        Map<String, String> headers=new HashMap<String, String>();
        Map<String, String> params=new HashMap<String, String>();
        Base64.getEncoder().encode("".getBytes());
        headers.put("Authorization","Basic dXNlci1zZXJ2aWNlOjEyMzQ1Ng==");
        params.put("grant_type","password");
        params.put("username",username);
        params.put("password",password);
        try {
            Result res = HttpClentUtils.post(url, headers, params, "UTF-8");
            System.out.println(">>>>>>>autho2-uaa返回:"+ res.getBody());
            JWT jwt=JSON.parseObject(res.getBody(),JWT.class);
            return jwt;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUser(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        log.info(">>>>>>>>获取用户信息:{}", JSON.toJSONString(user));
        return user;
    }

}
