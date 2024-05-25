package org.example.t1.service;

import org.example.t1.StaticValues;
import org.example.t1.dao.UserDao;
import org.example.t1.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;

/**
 * @author 张喆宇
 * @Description:
 * @date 2024/5/24 21:22
 */
@Service
public class LoginService {
    final
    UserDao userDao;
    //验证用户名和密码格式
    private static final String USERNAME_REGEX = "^[a-zA-Z][a-zA-Z0-9_-]{2,15}$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{6,20}$";

    private static boolean validate(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public LoginService(UserDao userDao) {
        this.userDao = userDao;
    }

    public String reg(String username, String password, String email, Integer code) {
        /*
        用户名长度通常在 3 到 16 个字符之间
        只能包含字母、数字、下划线或短划线
        必须以字母开头
        密码长度通常在 6 到 20 个字符之间
        必须包含至少一个字母和一个数字
        可以包含特殊字符
        */
        if(!validate(username, USERNAME_REGEX)){
            return "invalidUsername";
        }
        if(!validate(password, PASSWORD_REGEX)){
            return "invalidPassword";
        }
        if(userDao.findDistinctByUsername(username)!=null){
            return "usernameAlreadyExist";
        }
        if(userDao.findDistinctByEmail(email)!=null)
            return "emailAlreadyExist";
        //System.out.println(StaticValues.emailCode);
        //System.out.println(code);
        if(Objects.equals(StaticValues.emailCode, code)){
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            userDao.save(user);
            return "success";
        }else{
            return "errorEmailCode";
        }
    }

    public String login(String username, String password) {
        User user = userDao.findDistinctByUsername(username);
        if (user == null) {
            return "unregistered";
        }
        if (user.getPassword().equals(password)) {
            StaticValues.onlineUser=user;
            return "success";
        } else {
            return "wrongPassword";
        }
    }

    public List<User> allUsers(){
        return userDao.findAll();
    }
}
