package org.example.t1.controller;

import org.example.t1.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public String lol() {return "测试本地服务器能不能用";}
}
