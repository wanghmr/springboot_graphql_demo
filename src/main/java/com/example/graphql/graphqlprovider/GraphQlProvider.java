package com.example.graphql.graphqlprovider;

import com.example.graphql.datafetchers.TeacherDataFeather;
import com.example.graphql.pojo.Student;
import com.example.graphql.pojo.Teacher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

/**
 * @author wh
 * @date 2020/7/3
 * Description:
 */
@Component
public class GraphQlProvider {

    private GraphQL graphQl;
    private GraphQLSchema graphQLSchema;
    @Autowired
    private TeacherDataFeather teacherDataFeather;

    /**
     * 给spring注入配置好的GraphQL 外面可以调用
     */
    @Bean
    public GraphQL graphQl() {
        return this.graphQl;
    }

    @Bean
    public GraphQLSchema graphQLSchema() {
        return this.graphQLSchema;
    }

    /**
     * 实现对GraphQL对象的初始化
     *
     * @throws FileNotFoundException 异常
     */
    @PostConstruct
    public void init() throws FileNotFoundException {
        //加载resources目录下的文件
        File file = ResourceUtils.getFile("classpath:graphqls/teacher.graphqls");
        //创建GraphQLSchema
        graphQLSchema = createGraphQlSchema(file);
        //创建GraphQL
        this.graphQl = GraphQL.newGraphQL(graphQLSchema).build();
    }

    /**
     * 通过读取的.graphqls文件构建Schema
     *
     * @param file 文件
     * @return Schema
     */
    private GraphQLSchema createGraphQlSchema(File file) {
        //模式解析器
        SchemaParser schemaParser = new SchemaParser();
        //解析器解析.graphqls文件,返回类型定义注册表
        TypeDefinitionRegistry typeRegistry = schemaParser.parse(file);

        //创建模式生成器
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, buildResolver());
    }

    /**
     * 设置查找到的数据
     *
     * @return RuntimeWiring
     */
    private RuntimeWiring buildResolver() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        //无客户端（只是一个例子）
                        .dataFetcher("teacher",
                                dataFetchingEnvironment -> {
                                    //id：该对象设置的查询参数名
                                    int id = dataFetchingEnvironment.getArgument("id");
                                    //此处应该查询数据库（省略...）

                                    /*
                                     * 返回查询结果
                                     * 注意：返回的对象的类型 必须和查询时名称对应的实体类类型一致 不然会返回null
                                     * 这里指.graphqls文件中 以user进行查询会对应的类型User,类型User对应实体类User 所以不许返回User的对象
                                     */
                                    return new Teacher(id, "springboot+graphql", 15);
                                })
                        .dataFetcher("student",
                                dataFetchingEnvironment -> {
                                    int id = dataFetchingEnvironment.getArgument("id");
                                    return new Student(id, "小明", "北京");
                                })

                        //有客户端（前后端分离）
                        .dataFetcher("findList", teacherDataFeather.getList())
                )
                .build();

    }


}
