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
 * @date 2024/5/21 20:18
 */
@Data
@Entity(name = "quepaper")
public class QuePaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//问卷ID
    private Integer userId;//创建用户ID，外键
    private String title;//问卷标题
    private Date createTime;//问卷创建时间
    private Integer status;//状态值：0：未发布1：已发布2：已结束
    private Date startTime;//开始时间
    private Date endTime;//结束时间
}
