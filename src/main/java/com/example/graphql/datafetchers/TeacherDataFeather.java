package com.example.graphql.datafetchers;

import com.example.graphql.httpclient.HttpClient;
import com.example.graphql.httpclient.HttpHeaders;
import com.example.graphql.pojo.Teacher;
import com.example.graphql.query.TeacherQuery;
import com.google.gson.Gson;
import graphql.schema.DataFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author wh
 * @date 2020/7/9
 * Description: 数据读取器
 */
@Component
public class TeacherDataFeather {
    private final Logger logger = LoggerFactory.getLogger(TeacherDataFeather.class);
    private String techerGetListUrl="http://127.0.0.1:9092/manager/tercher/query";
    private final Charset charset = StandardCharsets.UTF_8;

    /**
     * 查询
     *
     * @return FsMerchantResponsModel
     */
    public DataFetcher<Teacher> getList() {
        logger.info("---->>>>FsMerchant getList ");
        return dataFetchingEnvironment -> {
            //请求头
            Map<String, String> headersMap = new HttpHeaders()
                    .addHeader("Content-Type", "application/json")
                    .getHeadersMap();

            //查询条件,转化为json串
            TeacherQuery query = new TeacherQuery();
            query.setId(dataFetchingEnvironment.getArgument("id"));
            query.setName(dataFetchingEnvironment.getArgument("name"));
            query.setAddress(dataFetchingEnvironment.getArgument("address"));
            Gson gson = new Gson();
            String req = gson.toJson(query);

            //调用后端controller
            String returnString = HttpClient.sendPost(techerGetListUrl, req, headersMap, charset);
            System.out.println("-------------------------"+returnString);
            return new Teacher(1,"小明",18);

        };
    }
}
