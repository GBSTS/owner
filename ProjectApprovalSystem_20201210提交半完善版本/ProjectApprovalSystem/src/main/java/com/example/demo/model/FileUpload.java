package com.example.demo.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileId;
    private String filePath;

    public FileUpload(int fileId, String filePath) {
        this.fileId = fileId;
        this.filePath = filePath;
    }
}