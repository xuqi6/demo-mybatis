package com.example.utils;

import java.sql.*;


public class DBUtils {

    private static String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    public static Connection getConnection(String url,String userName,String pwd) {
        try {
            Class.forName(driverClass);
            return DriverManager.getConnection(url, userName, pwd);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // 关闭所有资源的统一代码
    public static void closeAll(Connection conn, Statement st, ResultSet rs){
        //负责关闭
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(st != null){
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


