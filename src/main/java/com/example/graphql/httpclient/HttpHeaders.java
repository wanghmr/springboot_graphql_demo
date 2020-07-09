package com.example.graphql.httpclient;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wh
 * @date 2020/7/9
 * Description:
 */
public class HttpHeaders {

    public Map<String,String> headersMap=new HashMap<>();

    public HttpHeaders() {
    }

    public HttpHeaders  addHeader(String k, String v){
        headersMap.put(k,v);
        return this;
    }
    public  Map<String, String> getHeadersMap() {
        return headersMap;
    }


}
