package com.wuzhi.util;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public class ImgSave {
    String projectPath = System.getProperty("user.dir");//获取当前项目路径
    private final String saveImgUrl=projectPath+"\\src\\main\\resources\\img";

    public String SaveImg(MultipartFile file,String path,String fileName)throws Exception{
        String realPath=path+"/" +UUID.randomUUID().toString().replace("-", "")+fileName.substring(fileName.lastIndexOf("."));
        System.out.println(realPath);
        File dest = new File(realPath);
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
            //日志生成
            Logger logger=Logger.getLogger(Logger.class);
            logger.info("图片上传");
        }
        file.transferTo(dest);

        return dest.getName();
    }


}
