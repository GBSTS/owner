package com.example.demo.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Controller
public class FileController {
    @RequestMapping("/uploadfile")
    public String uploadfile(){
        return "uploadFile";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(HttpServletRequest request, HttpServletResponse response){
        System.out.println(request.getParameter("name"));
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("file");
        for(MultipartFile file:files) {
            if (!file.isEmpty()) {
                String path = "E:/" + "upload/";
                LocalDateTime now = LocalDateTimeUtil.now();
                int year = now.getYear();
                int month = now.getMonthValue();
                int day = now.getDayOfMonth();
                long milli = now.toInstant(ZoneOffset.of("+8")).toEpochMilli();
                path = path + year + month + day + "/";
                if(!new File(path).exists())
                    new File(path).mkdirs();
                String fileName = file.getOriginalFilename();
                File target = new File(path + milli + "_" + fileName);
                System.out.println("-------------------->" + target.getAbsolutePath());//返回当前文件的存储路径
                try {
                    file.transferTo(target);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                response.setCharacterEncoding("UTF-8");
                try {
                    response.getWriter().write(path + milli + "_" + fileName);
                    response.setHeader("content-type", "text/html;charset=UTF-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    response.flushBuffer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}