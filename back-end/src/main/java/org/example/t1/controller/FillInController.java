package org.example.t1.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.t1.service.CreateService;
import org.example.t1.service.FillInService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.repository.query.Param;
/**
 * @author 郑悦
 * @Description:用于填写问卷的接口 提交答案
 * @date 2024/5/24 22:01
 */
@RestController
public class FillInController {
    final CreateService createService;
    final FillInService fillInService;
    final Gson gson;

    public FillInController(CreateService createService, Gson gson, FillInService fillInService) {
        this.createService = createService;
        this.fillInService = fillInService;
        this.gson = gson;
    }

    @PostMapping("/api/fillin/submitAnswer")
    public String submitAnswer(@Param("quePaperId") Integer quePaperId, @RequestBody String answer, @Param("userId") Integer userId) {
        String answerListJson = gson.fromJson(answer, JsonObject.class).get("answerList").toString();
        return fillInService.submitAnswer(quePaperId, answerListJson, userId);
    }
}
