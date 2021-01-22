package com.cexper.cexperiment.controller;


import com.alibaba.fastjson.JSON;
import com.cexper.cexperiment.util.Common;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
public class FileController {


    @RequestMapping("/upload")
    public String index(){
        return "upload";
    }

    @RequestMapping("/upload1")
    public String index1(){
        Common.getJarRootPath("static/img");
        return "upload1";
    }

    @RequestMapping("/showuploadimg")
    @ResponseBody
    public String showuploadimg(String id,HttpSession session){
        String retStr="";


        return retStr;
    }

    @RequestMapping("/uploadImg")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file, Model model,HttpSession session)  {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        fileName ="a"+fileName;
        File dest = null;
        String os = System.getProperty("os.name");
//        System.out.println(os);
        if (os.toLowerCase().startsWith("win")) {
            String path = "E:"+File.separator+"img"+File.separator;
            path=Common.getJarRootPath("static/img");
            System.out.println(path);
            dest= new File(path +File.separator+ fileName);
        }else {
            String path = "/webapps/img/";
            dest= new File(path + fileName);
        }
        model.addAttribute("src","img/"+fileName);
        try {
            file.transferTo(dest);
            return JSON.toJSONString("上传成功！");
        } catch (IOException e) {
            return JSON.toJSONString("上传失败！");
        }

    }




}
