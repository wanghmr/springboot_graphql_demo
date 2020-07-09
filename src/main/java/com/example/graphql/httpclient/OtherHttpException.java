package com.example.graphql.httpclient;

/**
 * Http访问产生其他错误
 *
 * @author quhan
 */
public class OtherHttpException extends RuntimeException {

    public OtherHttpException(String message) {
        super(message);
    }
}
