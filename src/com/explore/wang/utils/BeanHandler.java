package com.explore.wang.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 返回bean对象的ResultSetHandler
 * <p>
 * 泛型需要被定义在类名之后
 * <p>
 * Created by 王兆琦  on 2016/12/8 14.18.
 */
public class BeanHandler<T> implements ResultSetHandler<T> {

    //因为要操作传入指定类型的bean对象,需要知道bean对象中的信息，
    // bean层不涉及到耦合，那么传入他的字节码文件到ResultHandler的构造方法中，来获取信息
    // 假如涉及到了耦合， 那么就得用反射获取了。

    private Class<T> clazz;

    public BeanHandler(Class<T> clazz) {
        this.clazz = clazz;
    }


    /**
     * 进行遍历resultSet，进行与bean对象属性值一致的参数进行设置，
     * 那么不一致的呢？  怎么处理？？
     * 因为需要循环设值， 那么就 continue;  舍弃这条数据
     */
    @Override
    public T handle(ResultSet resultSet) throws SQLException {

        //进行resultSet的判断
        if (resultSet.next()) {

            // 得到字节码中的信息
            try {
                T t = clazz.newInstance();

                // 利用 Introspector 得到这个字节码类中所有的参数信息
                BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

                // 得到其中的属性信息
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

                //进行属性配对设置
                for (int i = 0; i < propertyDescriptors.length; i++) {

                    //得到属性名
                    String attr = propertyDescriptors[i].getName();

                    // 得到set方法
                    Method method = propertyDescriptors[i].getWriteMethod();

                    // 此处 attr 出了属性值，还会有 class ，那么 result.getObject（“class”）是找不到，会抛出错误的
                    // 进行判断， 如果没有正确设置，则不要次条记录，continue 下次循环
                    try {
                        method.invoke(t, resultSet.getObject(attr));
                    } catch (Exception e) {
                        continue;
                    }
                }
                return t;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
