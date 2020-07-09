package com.example.graphql.service;

import com.example.graphql.pojo.GraphQlType;
import com.example.graphql.query.TeacherQuery;
import graphql.ExecutionInput;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wh
 * @date 2020/7/9
 * Description:
 */
@Service
public class TeacherServiceImpl {

    private final GraphQL graphQL;
    private GraphQLSchema graphQLSchema;

    @Autowired
    public TeacherServiceImpl(GraphQL graphQl, GraphQLSchema graphQLSchema) {
        this.graphQL = graphQl;
        this.graphQLSchema = graphQLSchema;
    }

    public Map<String, Object> findAll() {
        TeacherQuery query=new TeacherQuery();
        //schema
        String findList = packegeQueryMsg(query, GraphQlType.QUERY.getValue(), "findList");
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                // 需要执行的查询语言
                .query(findList)
                // 执行操作的名称，默认为null
                .build();
        LinkedHashMap data = (LinkedHashMap) this.graphQL.execute(executionInput).toSpecification();
        System.out.println("-----graphQl-----:"+data);
        return data;
    }

    /**
     * 组装查询schema包
     *
     * @param query    查询条件
     * @param type    查询/修改类型
     * @param function 查询所有的对象
     * @return 查询的所有值
     */
    private String packegeQueryMsg(TeacherQuery query, String type, String function) {
        StringBuilder builder = new StringBuilder();
        builder.append(type);
        builder.append(" {").append(function);
        if (query != null) {
            builder.append("(");
            if (query.getId() != null && !"".equals(query.getId())) {
                builder.append("id:").append("\"").append(query.getId()).append("\"").append(",");
            }
            if (query.getName() != null && !"".equals(query.getName())) {
                builder.append("name:").append("\"").append(query.getName()).append("\"").append(",");
            }
            if (query.getAddress() != null && !"".equals(query.getAddress())) {
                builder.append("address:").append("\"").append(query.getAddress()).append("\"").append(",");
            }
            if (",".equals(builder.substring(builder.length() - 1))) {
                builder.deleteCharAt(builder.length() - 1);
            }
            builder.append(")");
        }
        builder.append("{");
        graphQLSchema.getObjectType("Teacher").getFieldDefinitions().forEach(user -> {
            builder.append(user.getName()).append(" ");
        });
        //商户明细schema
        builder.append("{");
        graphQLSchema.getObjectType("Student").getFieldDefinitions().forEach(user -> {
            builder.append(user.getName()).append(" ");
        });
        builder.append("}");
        builder.append("}}");
        return builder.toString();
    }
}
