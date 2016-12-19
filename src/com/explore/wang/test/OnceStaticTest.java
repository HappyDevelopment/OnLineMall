package com.explore.wang.test;

/**
 *
 * 静态代码块是先于构造函数执行的 ，所有获取连接的时候， 可以在对象创建的时候，就获取连接。是优点
 * Created by 王兆琦  on 2016/12/15 16.55.
 */
public class OnceStaticTest {
    private static String string = null;
    static {

        string = "sdfsd";
    }

    public static void main(String[] args) {
        System.out.println(string);
        release();
        System.out.println(string);

    }

    public static void release(){
        string = null;
    }
}
