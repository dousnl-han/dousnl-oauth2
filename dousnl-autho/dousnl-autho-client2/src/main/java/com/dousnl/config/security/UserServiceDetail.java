package com.dousnl.config.security;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/8/26 18:22
 */
@Slf4j
@Service
public class UserServiceDetail implements UserDetailsService {

    @Value("${client_id}")
    private String clientId;
    @Value("${secret}")
    private String secret;


    private static final SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");

    private static final String CLIENT_SECRET_PRE="Basic ";

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

    public JWT execPost(String username, String password) {
        //dXNlci1zZXJ2aWNlOjEyMzQ1Ng== 是 user-service:123456的 base64编码
        //JWT jwt=client.getToken("Basic dXNlci1zZXJ2aWNlOjEyMzQ1Ng==", "password", username, password);
        String url = "http://localhost:8080/oauth/token";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> params = new HashMap<String, String>();
        String format = String.format("%s:%s", clientId, secret);
        String client = Base64.getEncoder().encodeToString(format.getBytes());
        headers.put("Authorization", CLIENT_SECRET_PRE + client);
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);
        try {
            Result res = HttpClentUtils.post(url, headers, params, "UTF-8");
            System.out.println(">>>>>>>autho2-uaa返回:" + res.getBody());
            JWT jwt = JSON.parseObject(res.getBody(), JWT.class);
            return jwt;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUser(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        log.info("时间：{} ，获取用户信息：{}", format.format(new Date()), JSON.toJSONString(user));
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userMapper.selectByUser(new User(s)));
        if (!user.isPresent()){
            throw new UsernameNotFoundException("invalid username or password...!");
        }
        return new ClientUserDetails(user.get());
    }

    private class ClientUserDetails implements UserDetails {

        private User user;
        public ClientUserDetails(User user) {
            this.user=user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
