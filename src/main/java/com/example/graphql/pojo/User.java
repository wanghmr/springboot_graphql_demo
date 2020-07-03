package com.example.graphql.pojo;

import lombok.Data;

/**
 * @author wh
 * @date 2020/7/3
 * Description:
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    public User(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

}
