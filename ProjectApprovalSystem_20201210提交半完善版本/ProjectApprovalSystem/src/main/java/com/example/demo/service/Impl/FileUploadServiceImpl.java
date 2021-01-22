package com.example.demo.service.Impl;

import com.example.demo.model.Apply;
import com.example.demo.model.FileUpload;
import com.example.demo.model.User;
import com.example.demo.service.FileUploadService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int insertFileUpload( String filePath) {
        String sql="insert into fileupload set filePath=?";
        jdbcTemplate.update(sql,new Object[]{filePath});
        sql="select * from fileupload where filePath=?";
        int fileId = jdbcTemplate.query(sql,(ResultSet rs)->{
            List<FileUpload> list = new ArrayList<>();
            while(rs.next()){
                FileUpload fileUpload = new FileUpload(rs.getInt("fileId"),rs.getString("filePath"));
                list.add(fileUpload);
            }
            return list.get(0).getFileId();
        },filePath);
        return fileId;
    }

    @Override
    public FileUpload getFileUploadByFileId(int fileId) {
        String sql="select * from fileupload where fileId=?";
        return jdbcTemplate.query(sql,(ResultSet rs)->{
            List<FileUpload> list = new ArrayList<>();
            while(rs.next()){
                FileUpload fileUpload = new FileUpload(rs.getInt("fileId"),rs.getString("filePath"));
                list.add(fileUpload);
            }
            return list.get(0);
        },fileId);
    }
}
