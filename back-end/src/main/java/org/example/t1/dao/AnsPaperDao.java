package org.example.t1.dao;

import org.example.t1.entity.AnsPaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author 郑悦
 * @Description:
 * @date 2024/5/24 21:20
 */
public interface AnsPaperDao extends JpaRepository<AnsPaper, Integer> {
    List<AnsPaper> findAllByUserId(Integer userId);

    AnsPaper findDistinctByQuePaperIdAndUserId(Integer quePaperId, Integer userId);
}
