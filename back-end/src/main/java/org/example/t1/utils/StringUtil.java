package org.example.t1.utils;

/**
 * @author 郑悦
 * @Description: 检查字符串方法——可以帮助检查问卷填写
 * @date 2024/5/25 01:35
 */
public class StringUtil {

    public  static boolean isEmpty(String s){
        if(s==null || s.equals("") || s.equals("null")){
            return true;
        }
        return false;
    }

    public  static boolean isNotEmpty(String s){
        return !StringUtil.isEmpty(s);
    }
}
