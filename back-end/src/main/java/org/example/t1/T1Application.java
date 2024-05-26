package org.example.t1;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class T1Application {

    public static void main(String[] args) {
        SpringApplication.run(T1Application.class, args);
    }

}
