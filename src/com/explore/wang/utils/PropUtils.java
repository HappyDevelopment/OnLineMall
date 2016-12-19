package com.explore.wang.utils;

import java.io.*;
import java.util.Properties;

/**
 * 读取配置文件的工具类
 * <p>
 * 需要单例模式
 * Created by 王兆琦  on 2016/12/5 20.11.
 */
public class PropUtils {

    //prop
    private static Properties properties = new Properties();


    //私有化构造函数, 不允许外检创建类的实例
    private PropUtils() {}

    static InputStream inputStream = null ;
    //需要在该类一出现就获取读取流读取资源文件，而且此方法只需要调用一次
    static {
        try {
            String path = PropUtils.class.getClassLoader().getResource("conf.properties").getPath();

            inputStream = new FileInputStream(new File(path));

            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    inputStream = null;
                }
            }
        }
    }


    /**
     * 用此方法获取配置文件中的类地址
     * @param key  要获取的配置文件key
     * @return     类的uri
     */
    public static String getValue(String key ) {

        return properties.getProperty(key);
    }

}
