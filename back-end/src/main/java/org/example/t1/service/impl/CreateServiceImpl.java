package org.example.t1.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.example.t1.StaticValues;
import org.example.t1.dao.QuePaperDao;
import org.example.t1.dao.QuestionDao;
import org.example.t1.dao.UserDao;
import org.example.t1.entity.QuePaper;
import org.example.t1.entity.Question;
import org.example.t1.entity.User;
import org.example.t1.service.CreateService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 郑悦
 * @Description:
 * @date 2024/5/24 22:29
 */
@Service
public class CreateServiceImpl implements CreateService {
    final QuestionDao questionDao;
    final QuePaperDao quePaperDao;
    final UserDao userDao;
    Gson gson = new Gson();

    public CreateServiceImpl(QuePaperDao quePaperDao, QuestionDao questionDao, UserDao userDao) {
        this.questionDao = questionDao;
        this.quePaperDao = quePaperDao;
        this.userDao = userDao;
    }
    //通过用户名新建问卷
    @Override
    public String createQuePaper(String username){
        QuePaper quePaper = new QuePaper();
        User user = userDao.findDistinctByUsername(username);
        quePaper.setUserId(user.getId());
        quePaper.setStatus(0);
        quePaper.setCreateTime(new Date());
        quePaper.setTitle("请输入标题");
        quePaperDao.save(quePaper);

        JsonObject res = new JsonObject();
        res.addProperty("id", quePaper.getId());

        return gson.toJson(res);
    }
    //通过问卷id删除问卷
    @Override
    public String deleteQuePaper(Integer quePaperId) {
        if(StaticValues.onlineUser.getId().equals(quePaperDao.findById(quePaperId).get().getUserId())){
            quePaperDao.deleteById(quePaperId);
            return "success";
        } else {
            return "denied";
        }
    }
    //获取问卷的问题列表
    @Override
    public String getQuestionList(Integer quePaperId) {
        JsonArray resList = new JsonArray();

        List<Question> questionList = questionDao.findAllByQuePaperId(quePaperId);
        for(Question oneQuestion : questionList){
            JsonObject oneRes = gson.fromJson(gson.toJson(oneQuestion), JsonObject.class);
            oneRes.addProperty("questionTitle", oneQuestion.getQuestionTitle());
            oneRes.addProperty("questionId",oneQuestion.getId());
            oneRes.addProperty("questionType",oneQuestion.getQuestionType());
            if(oneQuestion.getQuestionType()!=4){
                JsonObject temp = gson.fromJson(oneQuestion.getQuestionOption(), JsonObject.class);
                processDetails(oneRes, temp);
            }else {
                oneRes.addProperty("questionSum",oneQuestion.getQuestionSum());
            }
            resList.add(oneRes);
        }
        JsonObject res = new JsonObject();
        res.add("questionList", resList);
        return gson.toJson(res);
    }

    private void processDetails(JsonObject oneRes, JsonObject temp) {
        if (temp != null) {
            //这段多半要改，我没写那么多问题内容
            oneRes.add("questionOptions", temp.get("questionOptions").getAsJsonArray());
            oneRes.add("frontOptions", temp.get("frontOptions").getAsJsonArray());
            oneRes.addProperty("frontChoose", temp.get("frontChoose").getAsBoolean());
            oneRes.addProperty("numberType", temp.get("numberType").getAsString());
            oneRes.addProperty("defaultNumber", temp.get("defaultNumber").getAsInt());
            oneRes.addProperty("gradeMax", temp.get("gradeMax").getAsInt());
            oneRes.addProperty("date", temp.get("date").getAsString());
            oneRes.addProperty("textDescription", temp.get("textDescription").getAsString());
        }

    }

    //保存问卷标题内容等 主要用于修改问卷标题后使用
    @Override
    public String saveQuePaperOutline(String quePaper) {
        JsonObject quePaperObject = gson.fromJson(quePaper, JsonObject.class);
        QuePaper quePaperEntity = quePaperDao.findById(quePaperObject.get("quePaperId").getAsInt()).get();
        quePaperEntity.setTitle(quePaperObject.get("quePaperTitle").getAsString());
        quePaperEntity.setCreateTime(new Date());
        quePaperEntity.setStatus(0);
        return null;
    }
    //保存问卷(带问题)
    @Override
    public String saveQuePaper(String quePaper, String questionList) {
        JsonObject quePaperObject = gson.fromJson(quePaper, JsonObject.class);
        Integer quePaperId=quePaperObject.get("quePaperId").getAsInt();
        JsonArray questionListArray = gson.fromJson(questionList, JsonArray.class);
        saveQuePaperOutline(quePaper);

        for(JsonElement oneQuestionJson : questionListArray){
            JsonObject temp = oneQuestionJson.getAsJsonObject();
            saveOneQuestion(quePaperId, temp);
        }
        return "success";
    }
    //获取问卷标题内容等
    @Override
    public String getQuePaperOutline(Integer quePaperId) {
        JsonObject res = new JsonObject();
        res.add("quePaper", gson.fromJson(gson.toJson(quePaperDao.findById(quePaperId)), JsonObject.class));
        return gson.toJson(res);
    }
    //保存问题
    @Override
    public String saveOneQuestion(String question, Integer quePaperId) {
        JsonObject temp = gson.fromJson(question, JsonObject.class);;
        saveOneQuestion(quePaperId, temp);
        return "success";
    }
    //开放问卷
    @Override
    public String releaseQuePaper(Integer quePaperId) {
        QuePaper quePaper = quePaperDao.findById(quePaperId).get();
        quePaper.setStatus(1);
        quePaperDao.save(quePaper);
        return "success";
    }
    //关闭问卷
    @Override
    public String closeQuePaper(Integer quePaperId) {
        QuePaper quePaper = quePaperDao.findById(quePaperId).get();
        quePaper.setStatus(2);
        quePaperDao.save(quePaper);
        return "success";
    }

    private void saveOneQuestion(Integer quePaperId, JsonObject temp) {
        Question oneQuestion = new Question();
        oneQuestion.setQuePaperId(quePaperId);
        oneQuestion.setQuestionTitle(temp.get("questionTitle").getAsString());
        oneQuestion.setCreateTime(new Date());
        oneQuestion.setQuestionType(temp.get("questionType").getAsInt());
        if(oneQuestion.getQuestionType()!=4){
            JsonObject otherJson = new JsonObject();
            processDetails(otherJson, temp);
            oneQuestion.setQuestionOption(otherJson.toString());
        }else{
            oneQuestion.setQuestionSum(temp.get("questionSum").getAsInt());
        }
        questionDao.save(oneQuestion);
    }

}
