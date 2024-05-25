package org.example.t1.controller;

import org.example.t1.StaticValues;
import org.example.t1.entity.User;
import org.example.t1.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 张喆宇
 * @Description:
 * @date 2024/5/18 20:51
 */
@RestController
public class UserController {

    final
    LoginService loginService;

    public UserController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("api/users")
    public List query(){
        return loginService.allUsers();
    }

    @RequestMapping(value = "api/register", method = RequestMethod.POST)
    public String register(String username,String password,String email,Integer code){
        return loginService.reg(username, password,email,code);
    }

    @RequestMapping(value = "api/login", method = RequestMethod.GET)
    public String login(String username,String password){
        return loginService.login(username,password);
    }

}
