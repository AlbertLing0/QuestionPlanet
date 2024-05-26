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
 * @author 张喆宇
 * @Description:主要实现问卷创建部分的接口
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

    @GetMapping("/api/createQuePaper")
    public String createQuePaper(String username) {
        return createService.createQuePaper(username);
    }

    @PostMapping("/api/saveQuePaper")
    public String saveQuePaper(@RequestBody String body) {
        String questionList = gson.fromJson(body, JsonObject.class).get("questionList").toString();
        String quePaper = gson.fromJson(body, JsonObject.class).get("quePaper").toString();
        return createService.saveQuePaper(quePaper, questionList);
    }

    @PostMapping("/api/saveQuePaperOutline")
    public String saveQuePaperOutline(@RequestBody String quePaper) {
        return createService.saveQuePaperOutline(gson.fromJson(quePaper, JsonObject.class).get("quePaper").toString());
    }

    @PostMapping("/api/saveOneQuestion")
    public String saveOneQuestion(@RequestBody String oneQuestion, @Param("quePaperId") Integer quePaperId) {
        return createService.saveOneQuestion(gson.fromJson(oneQuestion, JsonObject.class).get("question").toString(), quePaperId);
    }

    @GetMapping("/api/deleteQuePaper")
    public String deleteQuestionnaire(@Param("quePaper") Integer quePaper) {
        return createService.deleteQuePaper(quePaper);
    }

    @GetMapping("/api/getQuestionList")
    public String getQuestionList(@Param("quePaperId") Integer quePaperId) {
        return createService.getQuestionList(quePaperId);
    }

    @GetMapping("/api/getQuePaperOutline")
    public String getQuestionnaireOutline(@Param("quePaperId") Integer quePaperId) {
        return createService.getQuePaperOutline(quePaperId);
    }

    @PostMapping("/api/releaseQuePaper")
    public String releaseQuePaper(@Param("quePaperId") Integer quePaperId) {
        return createService.releaseQuePaper(quePaperId);
    }

    @PostMapping("/api/closeQuePaper")
    public String closeQuestionnaire(@Param("quePaperId") Integer quePaperId) {
        return createService.closeQuePaper(quePaperId);
    }


}
