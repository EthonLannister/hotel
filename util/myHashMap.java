package edu.nju.hostelworld.util;

import java.util.*;
//定义一个HashMapSon类，它继承HashMap类
public class myHashMap<K, V> extends HashMap<K,V> {
    private static final long serialVersionUID = -5894887960346129860L;
    // 重写HashMapSon类的toString()方法
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<K,V> entry : entrySet()){
            sb.append("room: ").append(entry.getKey()).append("\n");
            sb.append("price difference: ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
        }
    }

