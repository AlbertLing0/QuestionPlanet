package org.example.t1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.t1.mapper")
public class T1Application {

    public static void main(String[] args) {
        SpringApplication.run(T1Application.class, args);
    }

}
