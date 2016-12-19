package com.explore.wang.factory;

import com.explore.wang.anno.Trans;
import com.explore.wang.dao.Dao;
import com.explore.wang.service.Service;
import com.explore.wang.utils.PropUtils;
import com.explore.wang.utils.TransManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 总的大型皮革厂 ，  包含 UserDao UserService
 * Created by 王兆琦  on 2016/12/5 20.35.
 */
public class CommonFactory {

    //静态成员变量，  只有一个实例
    private static final CommonFactory commonFactory = new CommonFactory();

    //私有构造函数
    private CommonFactory() {
    }

    //静态内部类达到线程安全
    private static class CommonFactoryHolder {
        private static final CommonFactory commonFactory = new CommonFactory();
    }

    /**
     * 获取到工厂类  ，
     *
     * @return commonFactory
     */
    public static CommonFactory getCommonFactory() {

        return CommonFactoryHolder.commonFactory;
    }


    /**
     * 传入类的字节码文件得好处是防止写错了， 利用泛型，来达到返回的就是索要的类
     *
     * @param clazz 想要得到的具体类的字节码
     * @param <T>   具体类
     * @return 具体类的对象
     */
    public <T> T getInstance(final Class<T> clazz) {
        try {
            String key = PropUtils.getValue(clazz.getSimpleName());

            Class clz = Class.forName(key);
            final T t = (T) clz.newInstance();  // 委托类，使用动态代理包装判断添加事务


            //查看是不是业务层， dao层是不用加事务的，  不出来事务逻辑
            if (Service.class.isAssignableFrom(clz)) {
//创建代理类，
                @SuppressWarnings("unchecked")
                T proxy = (T) Proxy.newProxyInstance(t.getClass().getClassLoader(),
                        t.getClass().getInterfaces(), new InvocationHandler() {

                            // 这里的invoke是代理类最终执行方法的地方， method为代理类方法
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                                //匿名内部类 ， 使用外面元素加final
                                // 开启与不开启事务只需要在这里进行方法的判断，即可， 这里是逻辑关键处
                                if (method.isAnnotationPresent(Trans.class)) {
                                    Object result = null;
                                    try {
                                        //开启事务
                                        TransManager.startTrans();

                                        //执行委托类的方法
                                        result = method.invoke(t, args);

                                        //提交事务
                                        TransManager.commitTrans();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                        //回滚
                                        TransManager.rollbackTrans();
                                        throw e.getTargetException();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        TransManager.rollbackTrans();
                                        throw new RuntimeException(e);
                                    } finally {

                                        //最终都是在这里释放连接资源
                                        TransManager.release();
                                    }

                                    return result;

                                } else {    //  不用开启事务

                                    Object result = null;

                                    try {
                                        result = method.invoke(t, args);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        throw new RuntimeException(e);
                                    } finally {

                                        //都是在这里关闭连接资源
                                        TransManager.release();
                                    }
                                    return result;
                                }
                            }
                        });

                return proxy;

            } else if (Dao.class.isAssignableFrom(clz)) {

                // dao层关不关闭连接资源？

                // service 调用多个dao的时候， 要是dao关闭，TransManger
                // get()方法会创建新的连接， 那么就不是同一个连接了。
                return t;
            } else {
                throw new RuntimeException("你是？？");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
}