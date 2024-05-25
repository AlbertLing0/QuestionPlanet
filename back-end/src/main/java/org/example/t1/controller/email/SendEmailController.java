package org.example.t1.controller.email;


import lombok.extern.slf4j.Slf4j;
import org.example.t1.StaticValues;
import org.example.t1.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;
/**
 * @author 张喆宇
 * @Description:
 * @date 2024/5/19 19:44
 */

@RestController
@Slf4j
public class SendEmailController {
    @Autowired
    private JavaMailSender mailSender;
    @PostMapping("api/sendemail")
    public int sendemail(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        int result=0;
        Random random = new Random();
        int i = random.nextInt(899999) + 100000;
        StaticValues.emailCode=i;
        //System.out.println(StaticValues.emailCode);
        message.setFrom("zp1772108@163.com");
        message.setTo(email);
        message.setSubject("问卷星球系统验证码");
        message.setText("邮箱验证码为: " + i+" ,请勿发送给他人");
        try {
            mailSender.send(message);
            log.info("验证码邮件已发送。");
        } catch (Exception e) {
            log.error("发送验证码邮件时发生异常了！", e);
            result =-1;
        }
        return result;
    }

}
