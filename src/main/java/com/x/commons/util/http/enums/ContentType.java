package com.x.commons.util.http.enums;

/**
 * @Desc TODO
 * @Date 2019-11-16 22:05
 * @Author AD
 */
public enum ContentType {
    JSON("application/json;charset=utf-8"),
    FORM("application/x-www-form-urlencoded;charset=utf-8");

    public String get() {
        return this.contentType;
    }

    private final String contentType;

    private ContentType(String contentType) {
        this.contentType = contentType;
    }

}
