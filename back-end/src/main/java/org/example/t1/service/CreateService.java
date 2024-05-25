package org.example.t1.service;

/**
 * @author 郑悦
 * @Description:
 * @date 2024/5/24 22:28
 */
public interface CreateService {
    /**
     * @Description  创建一份全新问卷
     * @param userid
     * @return java.lang.String
     * @author 郑悦
     * @date 2024/5/25 14:05
    **/
    String createQuestionnaire(Integer userid);

    String deleteQuestionnaire(Integer questionnaireId);

    /**
     * @Description  获取问题
     * @param questionnaireId
     * @return java.lang.String
     * @author 郑悦
     * @date 2024/5/25 14:05
    **/
    String getQuestionList(Integer questionnaireId);

    String saveQuestionnaireOutline(String questionnaire);

    String saveQuestionnaire(String questionnaire, String questionList);

    String getQuestionnaireOutline(Integer questionnaireId);

    String saveOneQuestion(String question, Integer questionnaireId);

    String releaseQuestionnaire(Integer questionnaireId);

    String closeQuestionnaire(Integer questionnaireId);
}
