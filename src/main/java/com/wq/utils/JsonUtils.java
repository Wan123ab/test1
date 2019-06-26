package com.wq.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wq.entity.User;

import java.io.IOException;
import java.util.Date;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T json2Obj(String json, Class<T> clz) {

        try {
            return mapper.readValue(json, clz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String obj2Json(Object o) {

        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static byte[] obj2ByteArr(Object o) {

        try {
            return mapper.writeValueAsBytes(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {

        User user = new User("1", "张三", new Date());

        String s = obj2Json(user);

        System.out.println(s);//{"userId":"1","name":"张三","createDate":"2019-04-18 16:08:41"}

        System.out.println(json2Obj(s, User.class));


    }


}
