package com.drugms.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = 401L;
    private Integer code;
    private String msg;
    private T data;
    private Map map=new HashMap();

    public static <T> R<T> success(T object){
        R<T> r = new R<>();
        r.data=object;
        r.code=200;
        return r;
    }

    public static <T> R<T> error(String msg){
        R<T> r = new R<>();
        r.msg=msg;
        r.code=400;
        return r;
    }

    public static <T> R<T> send(T object,Integer code){
        R<T> r = new R<>();
        r.data=object;
        r.code=code;
        return r;
    }
}
