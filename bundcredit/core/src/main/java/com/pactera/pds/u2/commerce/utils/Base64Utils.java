package com.pactera.pds.u2.commerce.utils;

import org.apache.commons.codec.binary.Base64;


public class Base64Utils {
    
    public static String encodeBase64(String code) {
        return new String(Base64.encodeBase64(code.getBytes(),false,true));
    }
    
    public static String decode(String code) {
        return new String(new Base64(true).decode(code));
    }
    public static byte[] decodeByte(String code){
        return new Base64(true).decode(code);
    }
    public static void main(String [] args){
        System.out.print(encodeBase64("Koala.jpg"));
        System.out.print(decode(encodeBase64("Koala.jpg")));
       
    }
}
