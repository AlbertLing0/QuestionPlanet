package org.example.t1.service;

/**
 * @author 郑悦
 * @Description:
 * @date 2024/5/24 21:27
 */
public interface FillInService {
    /**
     * @Description 接收前端的答卷json
     * @param ansPaperId
     * @param answerListJson
     * @param userId
     * @author 郑悦
     * @date 2024/5/24 21:48
    **/
    void submitAnswer(Integer ansPaperId, String answerListJson, Integer userId);

    /**
     * @Description 查询用户是否已经提交过这一问卷的答卷
     * @param ansPaperId
     * @param userId
     * @return java.lang.Boolean
     * @author 郑悦
     * @date 2024/5/24 21:47
    **/
    Boolean checkAlreadySubmit(Integer ansPaperId, Integer userId);
}
