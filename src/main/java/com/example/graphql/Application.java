package com.example.graphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 程序入口
 *
 * @author wh
 * <p>
 * 路径1：http://localhost:8080/graphql/teacher
 * 路径2：http://localhost:8080/graphql/student
 * <p>
 * 有客户端的路径
 * 路径3：http://localhost:8080/graphql/findAll
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
