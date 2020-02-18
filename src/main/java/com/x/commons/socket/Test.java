package com.x.commons.socket;

import java.util.Scanner;

/**
 * @Desc TODO
 * @Date 2020-02-18 20:03
 * @Author AD
 */
public class Test {
    
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("cmd:");
            String s = scanner.nextLine();
            System.out.println("---------------");
            System.out.println("cmd="+s+",receive=");
            System.out.println("content");
            System.out.println("---------------");
        }
    }
    
}
