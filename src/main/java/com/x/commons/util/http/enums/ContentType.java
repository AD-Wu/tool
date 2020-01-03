package com.x.commons.util.http.enums;

/**
 * @Desc
 * @Date 2019-11-16 22:05
 * @Author AD
 */
public enum ContentType {

    TEXT("text/plain"),
    XML("text/xml"),
    HTML("text/html"),
    JSON("application/json"),
    FORM("application/x-www-form-urlencoded");

    public String get() {
        return this.contentType;
    }

    private final String contentType;

    private ContentType(String contentType) {
        this.contentType = contentType;
    }

}
