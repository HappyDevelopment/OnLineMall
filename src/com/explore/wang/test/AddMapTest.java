package com.explore.wang.test;

import java.util.HashMap;
import java.util.Map;

/**
 * 保存map问题
 * Created by 王兆琦  on 2016/12/16 22.53.
 */
public class AddMapTest {

    static T t = new T();
    public static void main(String[] args) {

        Map<Integer,Integer> map = new HashMap();
        for (int i = 0; i < 5; i++) {
            map.put(i, i);
            System.out.println(map.toString());
        }
        t.setMap(map);

        System.out.println(t.getMap().size());        //   - --1
        System.out.println(t.getMap().toString());    ///   4-4
    }
}

class T {
    private Map map ;

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "T{" +
                "map=" + map +
                '}';
    }
}