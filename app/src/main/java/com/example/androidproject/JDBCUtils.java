package com;

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


    //释放资源
//    public static void close(Statement statement, Connection connection) {
//        if (statement != null) {
//            try {
//                statement.close();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
//        if (connection != null) {
//            try {
//                connection.close();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
//    }

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
            connection = DriverManager.getConnection("jdbc:mysql://121.199.39.117:3306/demo1", "root", "root");
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
            connection = DriverManager.getConnection("jdbc:mysql://121.199.39.117:3306/demo1", "root", "root");
            statement = connection.createStatement();
            update = statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return update;
    }


}
