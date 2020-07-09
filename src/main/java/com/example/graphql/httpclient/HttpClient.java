package com.example.graphql.httpclient;

import org.springframework.web.server.ServerErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * @author wh
 * @date 2020/7/9
 * Description:
 */
public class HttpClient {

    /**
     *
     * @param url 前后端分离的后端controller路径
     * @param param 查询条件（json串）
     * @param headersMap 请求头部信息
     * @param charset  编码
     * @return 返回信息
     */
    public static String sendPost(String url, String param, Map<String, String> headersMap, Charset charset) throws IOException {
        return sendRequest(url, param, headersMap, charset, HttpMethod.POST);
    }

    /**
     * @param url      发送请求的URL
     * @param param    请求参数
     * @param headersMap  请求头
     * @param charset 字符编码，默认为utf-8
     * @param post   请求方法
     * @return 响应消息
     * @throws IOException 错误信息
     */
    private static String sendRequest(String url, String param, Map<String, String> headersMap, Charset charset, HttpMethod post) throws IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            //连接超时时间
            conn.setConnectTimeout(1000);
            // 设置通用的请求属性
            if (headersMap != null) {
                Set<String> keySet = headersMap.keySet();
                for (String key : keySet) {
                    conn.setRequestProperty(key, headersMap.get(key));
                }
            }

            // 发送POST请求必须设置如下两行
            if (post == HttpMethod.POST) {
                conn.setDoOutput(true);
                conn.setDoInput(true);
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());

                out.print(param);
                out.flush();
            }

            // 定义BufferedReader输入流来读取URL的响应
            if (charset == null) {
                charset = StandardCharsets.UTF_8;
            }

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            } else {
                if (responseCode == 404) {
                    throw new UrlNotFoundException("找不到资源.404");
                } else if (responseCode == 500) {
                    throw new ServerErrorException("目标服务器内部错误.500");
                } else {
                    throw new OtherHttpException("其他错误，错误代码：" + responseCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            //使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
}
