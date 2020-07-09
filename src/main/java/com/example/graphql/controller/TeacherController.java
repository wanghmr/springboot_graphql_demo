package com.example.graphql.controller;

import com.example.graphql.service.TeacherServiceImpl;
import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author wh
 * @date 2020/7/3
 * Description: 由于没有GraphQL客户端调试软件,我就对controller改造了一下..模拟测试
 */
@Controller
@RequestMapping("/graphql")
public class TeacherController {

    private final GraphQL graphQL;
    @Autowired
    public TeacherController(GraphQL graphQl) {
        this.graphQL = graphQl;
    }
    @Autowired
    private TeacherServiceImpl teacherServiceImpl;

    /**
     * 无客户端的例子
     * @return
     */
    @GetMapping("/teacher")
    @ResponseBody
    public Map<String,Object> graphql01(){
        String query;
        query = "{teacher(id:1){id,name}}";
        return graphQL.execute(query).toSpecification();
    }

    /**
     * 无客户端的例子
     * @return
     */
    @GetMapping("/student")
    @ResponseBody
    public Map<String,Object> graphql02(){
        String query;
        query = "{student(id:1){id,address}}";
        return graphQL.execute(query).toSpecification();
    }

    /**
     * 客户端的例子
     * @return
     */
    @GetMapping("/findAll")
    @ResponseBody
    public Map<String,Object> findAllTeacher(){
        return teacherServiceImpl.findAll();
    }


}
