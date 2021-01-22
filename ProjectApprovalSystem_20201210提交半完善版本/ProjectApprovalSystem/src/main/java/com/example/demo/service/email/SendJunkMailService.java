package com.example.demo.service.email;

public interface SendJunkMailService {
    boolean sendJunkMail(String toMail, String subject, String content);
}