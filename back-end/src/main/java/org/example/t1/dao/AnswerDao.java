package org.example.t1.dao;

import lombok.Data;
import org.example.t1.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import java.util.List;

/**
 * @author 郑悦
 * @Description:
 * @date 2024/5/24 21:16
 */
public interface AnswerDao extends JpaRepository<Answer, Integer> {
    List<Answer> findAllByQuestionId(Integer questionId);

    List<Answer> findAllByAnsPaperId(Integer ansPaperId);
}
