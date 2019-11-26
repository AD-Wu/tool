package com.x.commons.util.bean;

/**
 * @Date 2018-12-19 20:44
 * @Author AD
 */
public final class SB {

    StringBuilder sb;

    SB() {
        sb = new StringBuilder();
    }

    public SB append(Object o) {
        sb.append(o);
        return this;
    }

    public SB deleteFirst() {
        if (sb.length() == 0) {
            return this;
        }
        sb.deleteCharAt(0);
        return this;
    }

    public SB deleteLast() {
        if (sb.length() == 0) {
            return this;
        }
        sb.deleteCharAt(sb.length() - 1);
        return this;
    }

    public int length() {
        return sb.length();
    }

    public String get() {
        return toString();
    }

    public String toString() {

        return sb.toString();
    }

    public static void main(String[] args) {
        SB sb = New.sb();
        sb.append("1").append(",").append("2").append(",");
        String s = sb.deleteFirst().get();
        System.out.println(s);
    }
}
