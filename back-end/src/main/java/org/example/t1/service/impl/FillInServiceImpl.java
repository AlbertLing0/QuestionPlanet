package org.example.t1.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.example.t1.dao.AnsPaperDao;
import org.example.t1.dao.AnswerDao;
import org.example.t1.dao.QuePaperDao;
import org.example.t1.entity.AnsPaper;
import org.example.t1.entity.Answer;
import org.example.t1.entity.QuePaper;
import org.example.t1.service.FillInService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author 郑悦
 * @Description:
 * @date 2024/5/24 21:27
 */
@Service
public class FillInServiceImpl implements FillInService {
    final AnswerDao answerDao;
    final AnsPaperDao ansPaperDao;
    final QuePaperDao quePaperDao;
    final Gson gson;

    public FillInServiceImpl(AnswerDao answerDao, QuePaperDao quePaperDao, Gson gson, AnsPaperDao ansPaperDao) {
        this.answerDao = answerDao;
        this.ansPaperDao = ansPaperDao;
        this.gson = gson;
        this.quePaperDao = quePaperDao;
    }

    @Override
    public String submitAnswer(Integer quePaperId, String answerListJson, Integer userId) {
        if(ansPaperDao.findDistinctByQuePaperIdAndUserId(quePaperId,userId)!=null){
            return "alreadyExist";
        }
        QuePaper quePaper = quePaperDao.findById(quePaperId).get();
        if(quePaper.getStatus()!=1){
            return "paperIsNotOpen";
        }

        AnsPaper ansPaper = new AnsPaper();
        ansPaper.setCreateTime(new Date());
        ansPaper.setTitle(quePaper.getTitle());
        ansPaper.setUserId(userId);
        ansPaper.setQuePaperId(quePaperId);
        JsonArray answerListArray = gson.fromJson(answerListJson, JsonArray.class);
        for(JsonElement oneAnswer : answerListArray){
            Answer answer = new Answer();
            answer.setAnsPaperId(ansPaper.getId());
            answer.setCreateTime(new Date());

            JsonObject oneAnswerObject = oneAnswer.getAsJsonObject();
            answer.setQuestionId(oneAnswerObject.get("questionId").getAsInt());
            answer.setAnswerTitle(oneAnswerObject.get("questionTitle").getAsString());
            Integer questionType = oneAnswerObject.get("questionType").getAsInt();
            answer.setQuestionType(questionType);

            if(questionType==1){
                answer.setAnswerContent(oneAnswerObject.get("answerOption").getAsString());
            }else if(questionType==2){
                answer.setAnswerContent(oneAnswerObject.get("answerOption").toString());
            }else if(questionType==3){
                answer.setAnswerContent(oneAnswerObject.get("answerText").getAsString());
            }else if(questionType==4){
                answer.setAnswerCode(oneAnswerObject.get("answerCode").getAsInt());
            }

            answerDao.save(answer);
        }
        return "success";
    }

    @Override
    public Boolean checkAlreadySubmit(Integer quePaperId, Integer userId) {

        return null;
    }
}
