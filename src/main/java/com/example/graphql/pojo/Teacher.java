package com.example.graphql.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author wh
 * @date 2020/7/3
 * Description:
 */
@Data
public class Teacher {
    private Integer id;
    private String name;
    private String gender;
    private Integer age;
    private String address;
    private String phone;
    private String email;
    private List<Student> content;

    public Teacher(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

}
