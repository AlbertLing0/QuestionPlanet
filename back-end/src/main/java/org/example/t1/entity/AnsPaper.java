package org.example.t1.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 张喆宇
 * @Description:
 * @date 2024/5/24 16:21
 */
@Data
@Entity(name = "anspaper")
public class AnsPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//答卷ID
    private Integer userId;//回答用户ID，外键
    private String title;//答卷标题
    private Date createTime;//答卷创建时间
}
