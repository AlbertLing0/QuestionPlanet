package org.example.t1.dao;

import org.example.t1.entity.QuePaper;
import org.example.t1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 张喆宇
 * @Description:
 * @date 2024/5/24 21:08
 */
public interface QuePaperDao extends JpaRepository<QuePaper, Integer> {
    List<QuePaper> findAllByUserId(Integer userId);
}
