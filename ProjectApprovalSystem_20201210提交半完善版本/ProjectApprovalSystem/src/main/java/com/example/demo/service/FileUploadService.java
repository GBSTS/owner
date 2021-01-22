package com.example.demo.service;

import com.example.demo.model.FileUpload;
import com.example.demo.model.User;

public interface FileUploadService {
    public int insertFileUpload(String filePath);
    public FileUpload getFileUploadByFileId(int fileId);
}
