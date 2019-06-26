package com.wq.dao;

public class UUID {

    public static String getId() {
        return java.util.UUID.randomUUID().toString().substring(0, 32).replaceAll("-", "");
    }

    public static String newShortId() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }


}
