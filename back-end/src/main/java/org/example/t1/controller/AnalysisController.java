package org.example.t1.controller;

import org.example.t1.service.AnalysisService;
import org.example.t1.utils.PrettifyUtil;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 郑悦
 * @Description:
 * @date 2024/5/26 19:41
 */
@RestController
public class AnalysisController {
    final AnalysisService analysisService;

    PrettifyUtil prettifyUtil = new PrettifyUtil();

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @GetMapping("/api/getQuePapers")
    public String getQuePaper(@Param("userId") Integer userId) {
        return analysisService.getQuePapersByUserId(userId);
    }

//    @GetMapping("/api/getQuePapers")
//    public String getQuesPaper(@Param("username") String username) {
//        return analysisService.getQuePapersByUsername(username);
//    }

    @GetMapping("/api/getQuestionValueList")
    public String getQuestionValueList(@Param("questionId") Integer questionId) {
        return analysisService.getQuestionValueList(questionId);
    }

    @GetMapping("/api/getAnswer")
    public String getAnswer(@Param("questionId") Integer questionId) {
        return analysisService.getAnswer(questionId);
    }

}
