package org.example.t1.dao;

import org.example.t1.entity.AnsPaper;
import org.example.t1.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 郑悦
 * @Description:
 * @date 2024/5/24 21:20
 */
public interface AnsPaperDao extends JpaRepository<AnsPaper, Integer> {
    List<AnsPaper> findAnsPaperById(Integer id);
}
