package com.cexper.cexperiment.util;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class Common {

    public static String getJarRootPath(String subpath)  {
        String path = null;
        try {
            path = ResourceUtils.getURL("classpath:").getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //=> file:/root/tmp/demo-springboot-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/
//        System.out.println("ResourceUtils.getURL(\"classpath:\").getPath() -> "+path);
        //创建File时会自动处理前缀和jar包路径问题  => /root/tmp
        if(path==null){
            path="C:/cexperment";
        }
//        System.out.println("ResourceUtils.getURL(\"classpath:\").getPath() -> "+path);
//        if(!path.substring(path.length()-1).equals("/")) path+="/";
        path +=subpath;
        File rootFile = new File(path);
        if(!rootFile.exists()) {
//            System.out.println("根目录不存在, 重新创建");
            rootFile.mkdirs(); //创建目录;

//            rootFile = new File(path);
//            System.out.println("重新创建的根目录: "+rootFile.getAbsolutePath());
        }
//        System.out.println("项目根目录: "+rootFile.getAbsolutePath());        //获取的字符串末尾没有分隔符 /
        return rootFile.getAbsolutePath();
    }




}
