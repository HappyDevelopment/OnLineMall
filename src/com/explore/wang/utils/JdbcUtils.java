package com.explore.wang.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * jdbc 的连接工具包 ， 得到连接池中的连接
 * 有框架来实现我的想法， 我是想用 utils来实现数据的查询与关闭资源的操作
 * Apache的 dbUtils 可以， 封装了这些。
 * <p>
 * Created by 王兆琦  on 2016/11/28 22.06.
 */
public class JdbcUtils {

    private static ComboPooledDataSource pool;

    //静态属性少写

    //  要放在静态代码块中， 防止出现使用了类，却不能有数据库的连接池
    // 每个dao中只有一个连接池的实例， 大家共享一个数据连接池，
    // 且只能是同一个， 要不然我出现过连接池没有连接了
    static {

        pool = new ComboPooledDataSource();
    }


    /**
     * 不写静态方法了， 那样内存占用挺多的，
     * 不过 utils 作为工具包， 还是要静态的阿
     *
     * @return 连接资源
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {

        //从 c3p0中 获取 连接

        //利用c3p0连接池来后去连接，他会默认根目录下的 c3p0-config.xml中的数据库信息
        return pool.getConnection();
    }


    /**
     * 关闭连接，其中外界不需要创建预处理传输器，调用方法时，直接传递参数即可
     * 我在关闭方法内部关闭
     * 算是我的装饰设计模式？
     * 我看他们关闭都是同步方法， 有一定的道理
     *
     * @param connection        连接资源
     * @param preparedStatement 传输器
     * @param resultSet         结果集
     */

    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {

        preparedStatement = preparedStatement;

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection = null;
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                preparedStatement = null;
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                resultSet = null;
            }
        }
    }


    /**
     * 模拟DBUtils 工具进行数据的更新操作
     *
     * @param sql    要执行的sql语句
     * @param params 可变Object参数
     * @return int值
     */
    public static int update(String sql, Object... params) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = TransManager.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            //遍历 params进行赋值
            for (int i = 0; i < params.length; i++) {

                preparedStatement.setObject(i + 1, params[i]);
            }

            int result = preparedStatement.executeUpdate();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();

            //这里抛出异常，上面有捕获
            throw new RuntimeException(e);
        } finally {
            close(null, preparedStatement, null);
        }
    }

    /**
     * 创造模拟DBUtils 进行查询操作，并且返回一个bean对象
     *
     * @param sql              sql 语句
     * @param resultSetHandler 结果集处理封装为bean对象的处理接口
     * @param params           可变参数
     * @param <T>              指定bean类型的字节码
     * @return 指定bean类型
     */
    public static <T> T query(String sql, ResultSetHandler<T> resultSetHandler, Object... params) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = TransManager.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            //遍历 params进行赋值
            for (int i = 0; i < params.length; i++) {

                preparedStatement.setObject(i + 1, params[i]);
            }

            resultSet = preparedStatement.executeQuery();

            return resultSetHandler.handle(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();

            // 写不写抛出RuntimeException？
            //  不写， 让程序继续运行，  关闭资源， 返回 null
            // 假如写了， 那么就就直接 程序终止， 让用户看到 RuntimeException 了，
            // 程序上线看到错误页面了，，， 这样不好， 查不到，数据有误
//            throw new RuntimeException(e);

        } finally {

            //不能在此处关闭资源了，动态代理处有关闭
            close(null, preparedStatement, resultSet);
        }

        // 为了 出错就返回null啊 啊啊啊啊啊
        return null;
    }


}
