package com.wuzhi.controlled;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhi.entity.CloImg;
import com.wuzhi.entity.CloType;
import com.wuzhi.entity.Clothing;
import com.wuzhi.entity.User;
import com.wuzhi.mapper.CloTypeMapper;
import com.wuzhi.mapper.ClothingMapper;
import com.wuzhi.mapper.StockMapper;
import com.wuzhi.mapper.UserMapper;
import com.wuzhi.util.addClothing;
import com.wuzhi.util.updateStock;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2020-11-18
 */
@RestController
@CrossOrigin
@RequestMapping("/clothing")
public class ClothingController {
    @Autowired
    private ClothingMapper clothingMapper;
    @Autowired
    private CloTypeMapper cloTypeMapper;
    @Autowired
    private StockMapper stockMapper;

    @GetMapping("/find/findAll")
    public List findAll() {
        List<Clothing> clothings = clothingMapper.selectList(null);
        System.out.println(clothings);
        return clothings;
    }

    @GetMapping("/find/findById/{id}")
    public Clothing findById(@PathVariable("id") int id) {
        String sava_path="localhost:8181\\img";
        String url=null;
        Clothing clothing = clothingMapper.selectById(id);
        url=clothing.getImgUrl();
        clothing.setImgUrl(sava_path+"\\"+url);
        return clothing;
    }
    //新增衣服
    @PutMapping("/addClo/{name}/{deposit}/{rent}/{cost}/{storeId}/{cloTypeId}/{num}")
    public String add(@PathVariable("name") String thisname,@PathVariable("deposit") int deposit,@PathVariable("rent") int rent,@PathVariable("cost") int cost,@PathVariable("storeId") int storeId,@PathVariable("cloTypeId") int cloTypeId,@PathVariable("num") int num){
        addClothing addClothing = new addClothing();
        String name=thisname;
        if (rent>(deposit/5)){
            return "租金过高，超过押金的五分之一";
        }
        if (deposit>(cost*1.5)){
            return "押金过高，超过销售价的1.5倍";
        }
        String result= addClothing.add(cloTypeMapper,clothingMapper,name,deposit,rent,cost,storeId,cloTypeId,num);
        updateStock updateStock1 = new updateStock();//刷新一下库存
        updateStock1.update(stockMapper,clothingMapper);
        updateStock1.addStock(stockMapper,clothingMapper);
        if (result=="该服装编号不存在"){
            return "添加成功！但是改服装编号未录入过，请添加该编号的服装类型描述";
        }else {
            return "录入成功！";
        }
    }
    //增加模糊查询like，根据衣服名称查找
    @GetMapping("/find/findCloByName/{name}")
    public List findCloByName(@PathVariable("name")String name){
        QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
        wrapper
                .like("name",name);
        List<Clothing> clothingList = clothingMapper.selectList(wrapper);
        String sava_path="localhost:8181\\img";
        String url=null;
        for (Clothing c:clothingList){
            url=c.getImgUrl();
            c.setImgUrl(sava_path+"\\"+url);
        }
        return clothingList;

    }
    //增加筛选，根据衣服的类型筛选
    @GetMapping("/find/findCloByType/{type}")
    public List<Clothing> findCloByType(@PathVariable("type")String type){
        QueryWrapper<CloType> wrapper = new QueryWrapper<>();
        wrapper
                .like("type",type);
        List<CloType> cloTypes = cloTypeMapper.selectList(wrapper);
        QueryWrapper<Clothing> wrapper1 =new QueryWrapper<>();
        for(CloType clo:cloTypes){
            wrapper.eq("clo_type_id",clo.getId());
        }
        List<Clothing> clothingList = clothingMapper.selectList(wrapper1);
        String sava_path="localhost:8181\\img";
        String url=null;
        for (Clothing c:clothingList){
            url=c.getImgUrl();
            c.setImgUrl(sava_path+"\\"+url);
        }
        return  clothingList;
    }
    //根据商家id查询商家的商品
    @GetMapping("/findCloById/{storeId}")
    public List findCloById (@PathVariable("storeId")int storeId){
        QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",storeId);
        List<Clothing> clothingList = clothingMapper.selectList(wrapper);
        String sava_path="localhost:8181\\img";
        String url=null;
        for (Clothing c:clothingList){
            url=c.getImgUrl();
            c.setImgUrl(sava_path+"\\"+url);
        }
        return  clothingList;
    }
    //查询当前商家的商品
    @GetMapping("/findThisStoreClo/{storeid}")
    public List findThisStoreClo(@PathVariable("storeid")int storeid){
        QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",storeid);
        List<Clothing> clothingList = clothingMapper.selectList(wrapper);
        String sava_path="localhost:8181\\img";
        String url=null;
        for (Clothing c:clothingList){
            url=c.getImgUrl();
            c.setImgUrl(sava_path+"\\"+url);
        }
        return  clothingList;
    }

    //获取首页衣服（注：需要和cloImgController的获取首页图片配合使用
    @PostMapping("/getAllCloInHomePage")
    public List getAllCloInHomePage (){
        String sava_path="localhost:8181\\img";
        String url=null;
        QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
        wrapper
                .eq("state","在库")
                .groupBy("store_id","clo_type_id")
                .isNotNull("img_url");
        List<Clothing> clothingList = clothingMapper.selectList(wrapper);
        for (Clothing c:clothingList){
            url=c.getImgUrl();
            c.setImgUrl(sava_path+"\\"+url);
        }
        return  clothingList;
    }

}
