/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.example.t1.entity;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    //用户总id
    public static int totalId=0;
    //用户id
    private int id;
    //用户名
    private String username;
    //用户密码
    private String password;
    //用户邮箱
    private String email;

    public User(String username, String password) {
        this.id=totalId;
        this.username = username;
        this.password = password;
        this.email="1348320402@qq.com";
        totalId++;
    }
}
