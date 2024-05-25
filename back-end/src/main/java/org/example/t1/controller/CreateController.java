package org.example.t1.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.t1.service.CreateService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 郑悦
 * @Description:
 * @date 2024/5/25 14:07
 */
@RestController
public class CreateController {
    final CreateService createService;
    final Gson gson;

    public CreateController(CreateService createService, Gson gson) {
        this.createService = createService;
        this.gson = gson;
    }

//    @GetMapping("/api/createQuestionnaire")
//    public String createQuestionnaire(@Param("userId") Integer userId) {
//        return createService.createQuestionnaire(userId);
//    }
//
//    @PostMapping("/api/saveQuestionnaire")
//    public String saveQuestionnaire(@RequestBody String body) {
//        String questionList = gson.fromJson(body, JsonObject.class).get("questionList").toString();
//        String questionnaire = gson.fromJson(body, JsonObject.class).get("questionnaire").toString();
//        return createService.saveQuestionnaire(questionnaire, questionList);
//    }
//
//    @PostMapping("/api/saveQuestionnaireOutline")
//    public String saveQuestionnaireOutline(@RequestBody String questionnaire) {
//        return createService.saveQuestionnaireOutline(gson.fromJson(questionnaire, JsonObject.class).get("questionnaire").toString());
//    }
//
//    @PostMapping("/api/saveOneQuestion")
//    public String saveOneQuestion(@RequestBody String oneQuestion, @Param("questionnaireId") Integer questionnaireId) {
//        return createService.saveOneQuestion(gson.fromJson(oneQuestion, JsonObject.class).get("question").toString(), questionnaireId);
//    }
//
//    @GetMapping("/api/deleteQuestionnaire")
//    public String deleteQuestionnaire(@Param("questionnaireId") Integer questionnaireId) {
//        return createService.deleteQuestionnaire(questionnaireId);
//    }
}
