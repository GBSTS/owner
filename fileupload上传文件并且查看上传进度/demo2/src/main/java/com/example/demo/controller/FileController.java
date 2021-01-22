package com.example.demo.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.example.demo.model.Progress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Controller
public class FileController {
    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    @RequestMapping(value = "/up",method = RequestMethod.POST)
    @ResponseBody
    public String up(HttpServletRequest request) throws IOException {
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
                String fileName = file.getOriginalFilename();
                File target = new File(path + milli + "_" + fileName);
                System.out.println("-------------------->" + target.getAbsolutePath());//返回当前文件的存储路径
                file.transferTo(target);
            }
        }
        return "upload success";
    }
    @GetMapping(value = "/size")
    @ResponseBody
    public Progress getProgress(HttpServletRequest request){
        HttpSession session = request.getSession();
        Progress progress = (Progress) session.getAttribute("status");
        System.out.println(progress + "controller");
        return progress;
    }
}
