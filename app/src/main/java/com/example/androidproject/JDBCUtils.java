/*
  ResultSet query(String sql);查询
  int update（String sql）；增删改
  void close();关闭连接

  6.8 15:38更新  解决数据库中文乱码问题
 */
package com.example.androidproject;

import java.sql.*;

public class JDBCUtils {
    static Connection connection = null;
    static Statement statement = null;
    static ResultSet resultSet = null;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("连接数据库...");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void close() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public static ResultSet query(String sql) {
//        Connection connection = null;
//        Statement statement = null;
//        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://121.199.39.117:3306/demo1?characterEncoding=utf-8", "root", "root");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //JDBCUtils.close(resultSet,statement,connection);


        return resultSet;
    }
    public static int update(String sql){
        int update = 0;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://121.199.39.117:3306/demo1?characterEncoding=utf-8", "root", "root");
            statement = connection.createStatement();
            update = statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return update;
    }


}
