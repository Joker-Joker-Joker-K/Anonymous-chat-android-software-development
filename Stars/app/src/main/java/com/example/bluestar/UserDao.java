package com.example.bluestar;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public static User login (String name, String password) {
        Connection conn = DBUtil.getConnection();
        String sql = "select * from user where name=? and password=?";
        User user = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            // bean d导入
            if(rs.next()){
                user = new User();
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
            DBUtil.release(rs, null, ps, conn);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public static boolean register(String name, String password) {
        Connection conn = DBUtil.getConnection();
        String sql = "insert into user(name,password) values(?,?)";
        PreparedStatement ps = null;
        int count = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,password);
            count = ps.executeUpdate();
            DBUtil.release(null,null,ps,conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(count == 0){
            return false;
        }else{
            return true;
        }
    }

    public static User findname(String name){
        Connection conn = DBUtil.getConnection();
        String sql = "select * from user where name=?";
        User user = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            // bean d导入
            if(rs.next()){
                user = new User();
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
            DBUtil.release(rs, null, ps, conn);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }


    public static String getUsername(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String username = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT name FROM user WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                username = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.release(rs, null, ps, conn);
        }

        return username;
    }

    public static String findUsersay(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String usersay = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT user_say FROM user WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                usersay = rs.getString("usersay");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.release(rs, null, ps, conn);
        }

        return usersay;
    }

    public static void updateUserName(int userId, String newName) {
        Connection conn = DBUtil.getConnection();
        if (conn == null){
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
        String sql = "UPDATE user SET name = ? WHERE id = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setInt(2, userId);
            ps.executeUpdate();
            DBUtil.release(null,null,ps,conn);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.release(null, null, ps, conn);
        }
    }

    public static void updateUserBio(int userId, String newBio) {
        Connection conn = DBUtil.getConnection();
        String sql = "UPDATE user SET user_say = ? WHERE id = ?";

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, newBio);
            ps.setInt(2, userId);
            ps.executeUpdate();
            DBUtil.release(null,null,ps,conn);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.release(null, null, ps, conn);
        }
    }
}
