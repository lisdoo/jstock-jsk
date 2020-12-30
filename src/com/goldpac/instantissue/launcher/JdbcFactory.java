package com.goldpac.instantissue.launcher;

import java.sql.*;
import java.util.ArrayList;

public class JdbcFactory {

    public static void main(String[] args) throws Exception {   //下面方法有不同的异常，我直接抛出一个大的异常

        //1、导入驱动jar包
        //2、注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        //3、获取数据库的连接对象
        Connection con = DriverManager.getConnection("jdbc:mysql://ali47:3306/jstock", "root", "lenovo.112");

        //4、定义sql语句
        String sql = "select code from jstock;";

        //5、获取执行sql语句的对象
        Statement stat = con.createStatement();

        //6、执行sql并接收返回结果
        ResultSet rs = stat.executeQuery(sql);

        //7、处理结果
        while (rs.next()) {
            System.out.println(
                rs.getString("code"));
        }


        //8、释放资源
        stat.close();
        con.close();
    }
}
