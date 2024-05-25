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
 * @date 2024/5/21 20:38
 */
@Data
@Entity(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//问题ID
    private String paperId;//问卷卷ID，外键
    private Date createTime;// 问题创建时间
    private Integer questionType;//问题类型：1：单选题2：多选题3：简答题4：打分题
    private String questionTitle;//问题标题
    private String questionOption;// 问题的选项：1.选择题：[option1,option2,option3...]2.简答题：空字符串
    private Integer questionSum;//打分题总分
}
