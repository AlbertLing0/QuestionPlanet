package org.example.t1.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author 张喆宇
 * @Description:
 * @date 2024/5/21 20:18
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Paper {
    private String id;//问卷卷ID
    private String userId;//用户ID，外键
    private String title;//问卷标题
    private Date createTime;//问卷创建时间
    private Integer status;//状态值：0：未发布1：已发布2：已结束
    private Date startTime;//开始时间
    private Date endTime;//结束时间
}
