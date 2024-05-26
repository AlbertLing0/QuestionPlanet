package org.example.t1.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author 郑悦
 * @Description: 用Gson解析JSON，返回易读的格式字符串
 * @date 2024/5/26 20:32
 */
public class PrettifyUtil {
    public String prettify(String json_text) {
        JsonObject json = JsonParser.parseString(json_text).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }
}
