package org.example.t1.service;

/**
 * @author 郑悦
 * @Description:
 * @date 2024/5/24 21:28
 */
public interface AnalysisService {
//    String getQuePapersByUsername(String username);
    String getQuePapersByUserId(Integer userId);

    String getQuePaperById(Integer quePaperId);

    String getAnswer(Integer questionId);

    String getQuestionValueList(Integer questionId);
}
