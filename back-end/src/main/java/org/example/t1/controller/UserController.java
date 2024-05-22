package org.example.t1.controller;

import org.example.t1.StaticValues;
import org.example.t1.entity.User;
import org.example.t1.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 张喆宇
 * @Description:
 * @date 2024/5/18 20:51
 */
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/users")
    public List query(){
        List<User> list = userMapper.findAll();
        return list;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(String username,String password){
        User user = new User(username, password);
        userMapper.insert(user);
        return "注册成功";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username,String password){
        User user = userMapper.findUserByName(username);
        if(user.getPassword().equals(password)) {
            StaticValues.onlineUser=user;
            return "登陆成功";
        }
        return "登陆失败";
    }

}
