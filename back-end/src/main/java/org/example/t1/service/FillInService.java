package org.example.t1.service;

/**
 * @author 郑悦
 * @Description:
 * @date 2024/5/24 21:27
 */
public interface FillInService {
    /**
     * @Description 接收前端的答卷json(注意，传进来的是问卷id，答卷id会自动生成）
     * @param quePaperId
     * @param answerListJson
     * @param userId
     * @author 郑悦
     * @date 2024/5/24 21:48
    **/
    String submitAnswer(Integer quePaperId, String answerListJson, Integer userId);
}
