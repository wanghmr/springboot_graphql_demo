package com.example.graphql.httpclient;

/**
 * 访问资源报404时，抛出该异常
 *
 * @author quhan
 */
public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String message) {
        super(message);
    }
}
