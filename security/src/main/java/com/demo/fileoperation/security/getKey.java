package com.demo.fileoperation.security;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class getKey {
    public static void main(String [] args){
        try {
            Map<String,String> map = SecrteKeyUtil.getKeys();
            Set set = map.entrySet();
            Iterator it = set.iterator();
            while (it.hasNext()){
                Map.Entry entry = (Map.Entry)it.next();
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
