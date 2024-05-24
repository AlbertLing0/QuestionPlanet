package org.example.t1.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;
/**
 * @author 张喆宇
 * @Description:
 * @date 2024/5/21 20:39
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    private String id;//答案ID
    private String ansPaperId;// 答卷ID，外键
    private String questionId;//问题ID，外键
    private Integer questionType;//问题类型：1：单选题2：多选题3：简答题 4：打分题
    private Date createTime;//答题时间
    private String answerTitle;//问题标题
    private String answerContent;//问题的答案：
    // 1.选择题：答案来自question表的question_option，[option1,option2,option3...]
    // 2.简答题：用户填写的文本
    // 3.打分题：空字符串
    private int answerCode;//答案得分（仅在打分题使用）
}
