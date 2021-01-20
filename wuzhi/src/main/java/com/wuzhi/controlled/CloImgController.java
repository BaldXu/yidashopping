package com.wuzhi.controlled;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhi.entity.CloImg;
import com.wuzhi.entity.Clothing;
import com.wuzhi.entity.User;
import com.wuzhi.mapper.CloImgMapper;
import com.wuzhi.mapper.ClothingMapper;
import com.wuzhi.util.ImgSave;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2020-11-24
 */
@RestController
@CrossOrigin
@RequestMapping("/clo-img")
public class CloImgController {
    @Autowired
    private CloImgMapper cloImgMapper;
    @Autowired
    private ClothingMapper clothingMapper;

    @RequestMapping("/upload/{cloTypeId}/{path}/{storeid}")//后面的path是要选择图片存储的文件夹，icon/clo_img/clo_intr_img  测试该方法需要先登录：http://localhost:8181/user/loginByemail/3333@qq.com/123456
    public String addDish(@RequestParam("Img") MultipartFile file, HttpServletRequest request,@PathVariable("cloTypeId") int cloTypeId,@PathVariable("path") String imgPath,@PathVariable("storeid") int storeid) throws Exception {
        String projectPath = System.getProperty("user.dir");//获取当前项目路径
        String sava_path=projectPath+"\\src\\main\\resources\\static\\img";
        System.out.println("sava_path:"+sava_path);
        String path = null;// 文件路径
        double fileSize = file.getSize();
        System.out.println("文件的大小是"+ fileSize);
        byte[] sizebyte=file.getBytes();
        System.out.println("文件的byte大小是"+ sizebyte.toString());
        if (file != null) {// 判断上传的文件是否为空
            String type = null;// 文件类型
            String fileName = file.getOriginalFilename();// 文件原名称
            System.out.println("上传的文件原名称:" + fileName);
            // 判断文件类型
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if (type != null) {// 判断文件类型是否为空
                if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {// 项目在容器中实际发布运行的根路径
                    path = sava_path+"\\"+imgPath+"\\"+fileName;//img/文件夹/文件名
                    // 转存文件到指定的路径
                    file.transferTo(new File(path));
                    System.out.println("文件成功上传到指定目录下:"+path);
                    //写入到数据库中
                    System.out.println("path:"+path+"  cloTypeId:"+cloTypeId+"  type:"+imgPath);
                    CloImg cloImg=new CloImg();
                    cloImg.setCloTypeId(cloTypeId);
                    cloImg.setImgUrl(imgPath+"\\"+fileName);//省略了前半的文件路径，之后读取的时候要加回来 sava_path
                    cloImg.setImgType(imgPath);
                    cloImg.setStoreId(storeid);
                    cloImgMapper.insert(cloImg);
                    if (imgPath.equals("icon")){//如果是首页图，则讲id写进衣服表里，方便查找
                        System.out.println("这个是首页图，即将写入首页图");
                        //修改所有符合条件的衣服
                        QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
                        wrapper
                                .eq("store_id",storeid)
                                .eq("clo_type_id",cloTypeId);
                        List<Clothing> clothingList= clothingMapper.selectList(wrapper);//此时是获得了所有商户下所有的typeId
                        System.out.println("写入首页图");
                        for (Clothing c:clothingList){
                            c.setImgUrl(imgPath+"\\"+fileName);
                            clothingMapper.updateById(c);
                        }
                    }else {
                        System.out.println(imgPath);
                    }
                    //日志生成
//                    Logger logger=Logger.getLogger(Logger.class);
//                    logger.info("图片上传："+path);
//                    return "文件成功上传到指定目录下";
                }
            } else {
                System.out.println("不是我们想要的文件类型,请按要求重新上传(现在只支持GIF/JPG/PNG三种图片类型)");
                return "SORRY，现在只支持GIF/JPG/PNG三种图片类型，请更换类型";
            }
        } else {
            System.out.println("文件类型为空");
            return "文件类型为空/无法获取文件类型";
        }

        return path;
    }

    @GetMapping("/findAll")
    public List findAll(){
        List<CloImg> cloImgList=cloImgMapper.selectList(null);
        return cloImgList;
    }
    //查询图片
    @GetMapping("/show/clo_info/img/{clo_type_id}/{img_type}/{store_id}")
    public String img(@PathVariable("clo_type_id")int clo_type_id,
                      @PathVariable("img_type")String img_type,
                      @PathVariable("store_id")String store_id){
        QueryWrapper<CloImg> wrapper = new QueryWrapper<>();
        String sava_path="localhost:8181\\img";
        wrapper
                .eq("clo_type_id",clo_type_id)
                .eq("store_id",store_id)
                .eq("img_type",img_type);
        List<CloImg> cloImgs= cloImgMapper.selectList(wrapper);
        if(cloImgs.size()!=0){
            CloImg cloImg=cloImgs.get(0);
            String path = sava_path+"\\"+cloImg.getImgUrl();
            return path;
        }
        else {
            return "无查找结果";
        }
    }
    //获取首页图片
//    @GetMapping("/getHomeImg")
//    public ArrayList getHomeImg(){
//        ArrayList<String> imgUrl=new ArrayList<>();
//        String projectPath = System.getProperty("user.dir");//获取当前项目路径
//        String sava_path=projectPath+"\\src\\main\\resources\\img";
//        String url=null;
//        //获取按照顺序的衣服
//        QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
//        wrapper
//                .groupBy("store_id","clo_type_id")
//                .isNotNull("img_id")
//                .orderByDesc("img_id")
//                .select("img_id");
//        List<Clothing> clothingList = clothingMapper.selectList(wrapper);//只有Img_id
//        //按照img_id获取图片
//        for (Clothing c:clothingList){
//            CloImg cloImg=cloImgMapper.selectById(c.getImgId());
//            url =cloImg.getImgUrl();
//            imgUrl.add(sava_path+"\\"+url);
//        }
//        return imgUrl;
//    }
    //根据商家id查找图片
    @GetMapping("/getStoreCloImg/{storeId}")
    public List getStoreCloImg(@PathVariable("storeId") int storeId){
        String projectPath = System.getProperty("user.dir");//获取当前项目路径
        String sava_path=projectPath+"localhost:8181\\img";
        String url=null;
        QueryWrapper<CloImg> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",storeId);
        List<CloImg> cloImgList= cloImgMapper.selectList(wrapper);
        for(CloImg c:cloImgList){
            url=c.getImgUrl();
            c.setImgUrl(sava_path+"\\"+url);
        }
        return cloImgList;
    }
}

