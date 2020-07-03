package com.example.graphql.GraphQLProvider;

import com.example.graphql.pojo.Card;
import com.example.graphql.pojo.User;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author wh
 * @date 2020/7/3
 * Description:
 */
@Component
public class GraphQLProvider {

    @Bean
    public GraphQL graphQL() {
        return this.graphQL;
    }

    private GraphQL graphQL;

    @PostConstruct
    public void init() throws FileNotFoundException {
        //加载resources目录下的文件
        File file = ResourceUtils.getFile("classpath:graphqls/user.graphqls");
        //创建GraphQLSchema
        GraphQLSchema graphQLSchema = createGraphQLSchema(file);
        //创建GraphQL
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }


    public GraphQLSchema createGraphQLSchema(File file) {
        //模式解析器
        SchemaParser schemaParser = new SchemaParser();
        //解析器解析graphqls文件,返回类型定义注册表
        TypeDefinitionRegistry typeRegistry = schemaParser.parse(file);

        //创建模式生成器
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, buildResolver());
    }

    public RuntimeWiring buildResolver() {
        return RuntimeWiring.newRuntimeWiring()
                .type("UserQuery", builder ->
                        builder.dataFetcher("user",
                                dataFetchingEnvironment -> {
                                    Long id = dataFetchingEnvironment.getArgument("id");
                                    return new User(id, "springboot+graphql", 15);
                                }).dataFetcher("card",
                                dataFetchingEnvironment -> {
                                    Long id = dataFetchingEnvironment.getArgument("id");
                                    return new Card(id, "futian");
                                })

                ).build();
    }


}
