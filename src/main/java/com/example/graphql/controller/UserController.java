package com.example.graphql.controller;

import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

/**
 * @author wh
 * @date 2020/7/3
 * Description: 由于没有GraphQL客户端调试软件,我就对controller改造了一下..模拟测试
 */
@Controller
public class UserController {
    @Autowired
    private GraphQL graphQL;

    @GetMapping("/graphql")
    @ResponseBody
    public Map<String,Object> graphql(){
        String query = null;
//        query = "{user(id:1){id,name}}";
        query = "{card(id:1){id,address}}";
        return graphQL.execute(query).toSpecification();
    }
}
