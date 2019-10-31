package com.demo.fileoperation.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    private static String driver
            = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/file?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static String username = "root";
    private static String password = "zsy2713";
    static Connection con = null;

    public static Connection getConnection(){
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            con = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (con != null){
            return con;
        }else {
            return null;
        }
    }
}
