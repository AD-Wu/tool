package com.x.protocol.enums;
/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/11 16:58
 */
public class ProtocolStatus {
    public static final int OTHERS = 0;
    public static final int OK = 1;
    public static final int METHOD_FAILURE = 2;
    public static final int REQUEST_FAILURE = 3;
    public static final int DATA_INVALID = 4;
    public static final int LOGIN_REQUIRED = 5;
    public static final int LICENSE_FORBIDDEN = 6;
    public static final int EXPECTATION = 7;
    public static final int WAITING_FOR_RESPONSE = 8;
    public static final int CONNECTION_CLOSED = 9;
    public static final int DEVICE_OFFLINE = 10;
}
