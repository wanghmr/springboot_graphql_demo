package com.example.graphql.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wh
 * @date 2020/7/3
 * Description:
 */
@Data
@AllArgsConstructor
public class Student {
    private Integer id;
    private String name;
    private String gender;
    private String address;

    public Student(Integer id, String address) {
        this.id = id;
        this.address = address;
    }

    public Student(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
