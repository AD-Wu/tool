package com.x.commons.util.json.test;

import lombok.Data;

/**
 * @Desc TODO
 * @Date 2019-10-29 23:06
 * @Author AD
 */
@Data
public class User {

    private String name = "AD";
    private int age = 28;
    private boolean sex = true;
    private Addr addr = new Addr();

}
