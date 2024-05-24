package org.example.t1.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author 张喆宇
 * @Description:
 * @date 2024/5/24 16:21
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AnsPaper {
    private String id;//答卷卷ID
    private String userId;//用户ID，外键
    private String title;//答卷标题
    private Date createTime;//答卷创建时间
}
