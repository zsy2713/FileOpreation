package com.demo.fileoperation.dao.impl;

import com.demo.fileoperation.dao.FileDao;
import com.demo.fileoperation.domain.Info;
import com.demo.fileoperation.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 原生jdbc连接数据库并操作
 */
public class FileDaoImpl implements FileDao {
    Connection con = null;
    PreparedStatement ps = null;
    //文件信息的插入
    public int insertInfo(Info info) {
        String sql = "insert into info (size,type,filename,date,mkdir,uuid) values (?,?,?,?,?,?)";
        con = JDBCUtil.getConnection();
        int flag = 0;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,info.getSize());
            ps.setString(2,info.getType());
            ps.setString(3,info.getFilename());
            ps.setString(4,info.getDate());
            ps.setString(5,info.getMkdir());
            ps.setString(6,info.getUuid());
            flag = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    //文件列表的获取
    public List<Info> findAll() {
        List<Info> list = new ArrayList<Info>();
        con = JDBCUtil.getConnection();
        String sql = "select * from info";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Info info = new Info();
                String size = rs.getString("size");
                String type = rs.getString("type");
                String filename = rs.getString("filename");
                String date = rs.getString("date");
                String mkdir = rs.getString("mkdir");
                String uuid = rs.getString("uuid");
                info.setSize(size);
                info.setMkdir(mkdir);
                info.setType(type);
                info.setDate(date);
                info.setFilename(filename);
                info.setUuid(uuid);
                list.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Info getInfo(String uuid) {
        con = JDBCUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from info where uuid = ?";
        Info info = new Info();
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,uuid);
            rs = ps.executeQuery();
            while(rs.next()){
                String type = rs.getString("type");
                String mkdir = rs.getString("mkdir");
                info.setType(type);
                info.setMkdir(mkdir);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return info;
    }
}
