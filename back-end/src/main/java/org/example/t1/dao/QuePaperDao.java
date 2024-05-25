package org.example.t1.dao;

import org.example.t1.entity.QuePaper;
import org.example.t1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 张喆宇, 郑悦
 * @Description:
 * @date 2024/5/24 21:08
 */
public interface QuePaperDao extends JpaRepository<QuePaper, Integer> {
    List<QuePaper> findAllByUserId(Integer userId);
}
