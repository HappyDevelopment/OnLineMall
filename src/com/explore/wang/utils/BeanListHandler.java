package com.explore.wang.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 返回一个list封装的bean 集合 的工具类
 *
 * 这里是有返回值得问题的，  要求返回一个List集合的bean ， 怎么传泛型？？
 *
 *   可以把接口中的泛型定义为  list<T> 类型 ， 那么接口中的方法返回的就是List集合的bean
 *
 *   因为接口中的方法返回类型要与接口类上定义的类型一致，  这样才能返回List集合的bean
 *
 *   那么这个类在覆盖接口中的方法的时候，返回值就是List集合的bean了
 *
 *   但是这个类返回类型必须是是<T>
 *
 * Created by 王兆琦  on 2016/12/8 20.43.
 */
public class BeanListHandler<T>  implements ResultSetHandler<List<T>>{

    // 传入的bean
    private Class<T>  clazz ;

    /**
     * 插入字节码文件来操作bean
     * @param tClass 传入的字节码文件
     */
    public BeanListHandler(Class<T> tClass) {

        this.clazz = tClass;
    }


    /**
     *
     * 返回list装的bean
     * @param resultSet  jiguoji
     * @return      list装的bean
     * @throws SQLException
     */
    @Override
    public List<T> handle(ResultSet resultSet) throws SQLException {

        List<T> list = new ArrayList<T>();

        while (resultSet.next()) {
            try {
                T t = clazz.newInstance();

                //得到类所有的信息
                BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

                // 得到类中的属性信息
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

                //遍历属性信息， 进行配对
                for (int i = 0; i < propertyDescriptors.length; i++) {

                    // 得到属性名
                    String attr = propertyDescriptors[i].getName();

                    // 得到 set方法
                    Method method = propertyDescriptors[i].getWriteMethod();

//                    System.out.println(method.toString());
//                    System.out.println(method.getParameterTypes()[0].equals(int.class));
//
//                    System.out.println(propertyDescriptors[i].getName());
//                    System.out.println(propertyDescriptors[i].getPropertyType()  == Integer.TYPE);

//                    这两种都可以实现，通过判断找到 SaleInfo中的sale_num
//                    因为在数据库的 sum求商品销售和时，返回的是  BigDecimal类型
//                    类型不匹配导致赋值失败

                    try {
                        Object value = null;
                        if(method.getParameterTypes()[0].equals(int.class)){
                            value = resultSet.getInt(attr);
                        }else {
                            //其他情况没事
                            value = resultSet.getObject(attr);
                        }

                        method.invoke(t, value);
                    } catch (Exception e) {
                        continue;
                    }
                }


                // 添加 bean 进入 list 中，  此步骤必须在设置完全部信息后进行
                //  要在 for里面进行的话， 会 添加不完整数据， 此时没有全部set完毕

                // 明白这段代码的逻辑 ， 但自己写就写不出来，
                // 还是没有深刻的理解其中的逻辑顺序。 这我的不足之处
                list.add(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list.size() == 0 ? null : list;
    }

}
