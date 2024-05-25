package org.example.t1.dao;

import org.example.t1.entity.Question;
import org.example.t1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 张喆宇
 * @Description:
 * @date 2024/5/24 21:08
 */
public interface QuestionDao extends JpaRepository<Question, Integer> {
    List<Question> findAllByQuePaperId(Integer quePaperId);

}
