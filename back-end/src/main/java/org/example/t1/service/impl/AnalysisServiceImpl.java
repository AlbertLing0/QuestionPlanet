package org.example.t1.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.example.t1.dao.AnswerDao;
import org.example.t1.dao.QuePaperDao;
import org.example.t1.dao.QuestionDao;
import org.example.t1.entity.Answer;
import org.example.t1.entity.Question;
import org.example.t1.entity.QuePaper;
import org.example.t1.service.AnalysisService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 郑悦
 * @Description:
 * @date 2024/5/24 21:28
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {
    final QuePaperDao quePaperDao;
    final QuestionDao questionDao;
    final AnswerDao answerDao;
    Gson gson = new Gson();

    public AnalysisServiceImpl(QuePaperDao quePaperDao, QuestionDao questionDao, AnswerDao answerDao) {
        this.quePaperDao = quePaperDao;
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

//    @Override
//    @Transactional
//    public String getQuePapersByUsername(String username) {
//        JsonObject res = new JsonObject();
//        res.add("questionnaires", gson.fromJson(gson.toJson(quePaperDao.findAllByUsername(username)), JsonArray.class));
//        return gson.toJson(res);
//    }

    @Override
    public String getQuePapersByUserId(Integer userId) {
        JsonObject res = new JsonObject();
        res.add("questionnaires", gson.fromJson(gson.toJson(quePaperDao.findAllByUserId(userId)), JsonArray.class));
        return gson.toJson(res);
    }

    @Override
    public String getQuePaperById(Integer quePaperId) {
        return gson.toJson(quePaperDao.findById(quePaperId));
    }

    @Override
    public String getAnswer(Integer questionId) {
        return gson.toJson(answerDao.findAllByQuestionId(questionId));
    }

    @Transactional
    @Override
    public String getQuestionValueList(Integer questionId) {
        List<Answer> answerList = answerDao.findAllByQuestionId(questionId);

        Question question = questionDao.findDistinctById(questionId);
        Integer questionType = question.getQuestionType();
        //问题类型：1：单选题2：多选题3：简答题4：打分题

        if (questionType == 1) {
            Map<String, Integer> resValueMap = new HashMap<>();
            JsonObject temp = gson.fromJson(question.getQuestionOption(), JsonObject.class);
            JsonArray questionOptionsJsonArray = temp.get("questionOptions").getAsJsonArray();
            for (JsonElement questionOptionJson : questionOptionsJsonArray) {
                String questionOption = questionOptionJson.getAsString();
                resValueMap.put(questionOption, 0);
            }
            System.out.println(temp);
            System.out.println(resValueMap);
            for (Answer oneAnswer : answerList) {
                String value = oneAnswer.getAnswerContent();
                Integer oldCount = resValueMap.get(value);
                resValueMap.put(value, oldCount + 1);
            }
            return gson.toJson(resValueMap);
        } else if (questionType == 2) {
            Map<String, Integer> resValueMap = new HashMap<>();
            JsonObject temp = gson.fromJson(question.getQuestionOption(), JsonObject.class);
            JsonArray questionOptionsJsonArray = temp.get("questionOptions").getAsJsonArray();
            for (JsonElement questionOptionJson : questionOptionsJsonArray) {
                String questionOption = questionOptionJson.getAsString();
                resValueMap.put(questionOption, 0);
            }
            for (Answer oneAnswer : answerList) {
                JsonArray valueList = gson.fromJson(oneAnswer.getAnswerContent(), JsonArray.class);
                for (JsonElement value : valueList) {
                    Integer oldCount = resValueMap.get(value.getAsString());
                    resValueMap.put(value.getAsString(), oldCount + 1);
                }
            }
            return gson.toJson(resValueMap);
        } else if (questionType == 4) { // 打分题
            Map<String, Double> resValueMap = new HashMap<>();
            List<Double> valueList = new ArrayList<>();
            Double sum = 0.0;
            for (Answer oneAnswer : answerList) {
                Double value = (double) oneAnswer.getAnswerCode();
                valueList.add(value);
                sum += value;
            }
            valueList.sort((a, b) -> (int) (a - b));
            System.out.println(valueList);

            if (valueList.size() == 0) {
                resValueMap.put("最大值", 0.0);
                resValueMap.put("最小值", 0.0);
                resValueMap.put("平均值", 0.0);
                resValueMap.put("中位数", 0.0);
            } else {
                resValueMap.put("最大值", valueList.get(valueList.size() - 1));
                resValueMap.put("最小值", valueList.get(0));
                resValueMap.put("平均值", sum / valueList.size());
                resValueMap.put("中位数", valueList.get(valueList.size() / 2));
            }

            return gson.toJson(resValueMap);
        }
        return null;
    }
}
