package org.example.t1.service.impl;

import com.google.gson.Gson;
import org.example.t1.dao.QuePaperDao;
import org.example.t1.dao.QuestionDao;
import org.example.t1.service.CreateService;
import org.springframework.stereotype.Service;

/**
 * @author 郑悦
 * @Description:
 * @date 2024/5/24 22:29
 */
@Service
public class CreateServiceImpl implements CreateService {
    final QuestionDao questionDao;
    final QuePaperDao quePaperDao;

    Gson gson = new Gson();

    public CreateServiceImpl(QuePaperDao quePaperDao, QuestionDao questionDao) {
        this.questionDao = questionDao;
        this.quePaperDao = quePaperDao;
    }

    @Override
    public String createQuestionnaire(Integer userid) {
        return null;
    }

    @Override
    public String deleteQuestionnaire(Integer questionnaireId) {
        return null;
    }

    @Override
    public String getQuestionList(Integer questionnaireId) {
        return null;
    }

    @Override
    public String saveQuestionnaireOutline(String questionnaire) {
        return null;
    }

    @Override
    public String saveQuestionnaire(String questionnaire, String questionList) {
        return null;
    }

    @Override
    public String getQuestionnaireOutline(Integer questionnaireId) {
        return null;
    }

    @Override
    public String saveOneQuestion(String question, Integer questionnaireId) {
        return null;
    }

    @Override
    public String releaseQuestionnaire(Integer questionnaireId) {
        return null;
    }

    @Override
    public String closeQuestionnaire(Integer questionnaireId) {
        return null;
    }
}
