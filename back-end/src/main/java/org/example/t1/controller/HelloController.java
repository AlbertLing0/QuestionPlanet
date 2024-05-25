package org.example.t1.controller;

import org.example.t1.StaticValues;
import org.example.t1.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 张喆宇
 * @Description:
 * @date 2024/5/16 20:53
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {return "你好 世界";}

    @GetMapping("/lol")
    public String lol() {
        System.out.println(StaticValues.onlineUser.getUsername());
        return "测试本地服务器能不能用";
    }
}
