package com.x.commons.util.bean;

/**
 * @Date 2018-12-19 20:44
 * @Author AD
 */
public final class SB {
    
    private StringBuilder sb;
    
    SB() {
        sb = new StringBuilder();
    }
    
    SB(String s) {sb = new StringBuilder(s);}
    
    public SB append(Object o) {
        sb.append(o);
        return this;
    }
    
    public SB preAppend(Object o) {
        sb.insert(0, o);
        return this;
    }
    
    public String sub(int start, int end) {
        return sb.substring(start, end);
    }
    
    public String sub(int start) {
        return sb.substring(start);
    }
    
    public SB insert(int offset, Object o) {
        sb.reverse();
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
        StringBuilder sb = new StringBuilder();
        sb.append("ABCD");
        sb.insert(0, 0).insert(0, 0);
        System.out.println(sb.toString());
    }
    
}
