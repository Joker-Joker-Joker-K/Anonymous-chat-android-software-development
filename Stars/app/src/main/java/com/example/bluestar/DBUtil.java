package com.example.bluestar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    //接口服务器

    private static final String databaseName = "android_test"; // 数据库名称
    private static final String ip = "rm-cn-pe339dysm0016r8o.rwlb.rds.aliyuncs.com"; // 主机名
    private static final String port = "3306"; // 端口号 例如 mysql 的默认端口号为 3306
    private static final String user = "testuser";// 用户名
    private static final String password = "Qwe13681045826";// 密码
    private static String url = "jdbc:mysql://" + ip + ":" + port + "/" + databaseName;
    //注册msql驱动
    static{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url,user,password);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }


    public static Connection getConnection(){
        Connection conn = null;
        try{
            System.out.println(url);
            conn = DriverManager.getConnection(url,user,password);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    public static void release(ResultSet rs, Statement st, PreparedStatement ps, Connection conn){
        try {
            if(rs != null){
                rs.close();
            }
            if(st != null){
                st.close();
            }
            if(ps != null) {
                ps.close();
            }
            if(conn != null){
                conn.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
