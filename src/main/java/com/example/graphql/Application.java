package com.example.graphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 程序入口
 * @author wh
 *
 * 路径1：http://localhost:8080/graphql/teacher
 * 路径2：http://localhost:8080/graphql/student
 *
 * 路径3：http://localhost:8080/graphql/student
 *
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
