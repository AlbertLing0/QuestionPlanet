package org.example.t1.service.impl;

import com.google.gson.Gson;
import org.example.t1.dao.AnsPaperDao;
import org.example.t1.dao.AnswerDao;
import org.example.t1.dao.QuePaperDao;
import org.example.t1.service.FillInService;
import org.springframework.stereotype.Service;

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
    public void submitAnswer(Integer ansPaperId, String answerListJson, Integer userId) {

    }

    @Override
    public Boolean checkAlreadySubmit(Integer ansPaperId, Integer userId) {
        return null;
    }
}
