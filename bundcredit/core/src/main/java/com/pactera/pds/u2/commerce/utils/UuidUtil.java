package com.pactera.pds.u2.commerce.utils;

import java.util.UUID;

/**
 * uuid生成
 * @author luogang
 *
 */
public class UuidUtil {
    public static String getUuid(){
        String result;
        result=UUID.randomUUID().toString().replaceAll("-", "");
        return result;
    }
    public static void main(String[] args) {
        System.err.println("booo:and:foo".split("o"));
    }
}
