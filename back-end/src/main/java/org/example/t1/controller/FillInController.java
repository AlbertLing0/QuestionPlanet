package org.example.t1.controller;

import com.google.gson.Gson;
import org.example.t1.service.CreateService;
import org.example.t1.service.FillInService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 郑悦
 * @Description:
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
}
