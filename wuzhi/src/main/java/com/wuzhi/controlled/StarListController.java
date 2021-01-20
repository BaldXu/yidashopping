package com.wuzhi.controlled;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhi.entity.Clothing;
import com.wuzhi.entity.ShoppingCart;
import com.wuzhi.entity.StarList;
import com.wuzhi.mapper.ClothingMapper;
import com.wuzhi.mapper.StarListMapper;
import com.wuzhi.util.buy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2020-12-03
 */
@RestController
@CrossOrigin
@RequestMapping("/star-list")
public class StarListController {
    @Autowired
    private StarListMapper starListMapper;
    @Autowired
    private ClothingMapper clothingMapper;
    //收藏
    @PostMapping("/add/{clo_type_id}/{storeId}/{userId}")
    public String add(@PathVariable("clo_type_id")int clo_type_id, @PathVariable("storeId")int storeId, @PathVariable("userId")int userId){
        //先判断是否已经存在
        QueryWrapper<StarList> wrapper=new QueryWrapper<>();
        wrapper
                .eq("clo_type_id",clo_type_id)
                .eq("store_id",storeId);
        List<StarList> starLists=starListMapper.selectList(wrapper);
        if (starLists.size()!=0){
            return "该商品已经收藏，请勿重复收藏";
        }
        //添加
        StarList starList = new StarList();
        starList.setUserId(userId);
        starList.setCloTypeId(clo_type_id);
        starList.setStoreId(storeId);
        //根据商户ID和服装编号查出单价和总价
        HashMap<String, Object> map = new HashMap<>();
        map.put("store_id",storeId);
        map.put("clo_type_id",clo_type_id);
        List<Clothing> clothing = clothingMapper.selectByMap(map);
        int cost=clothing.get(0).getCost();
        starList.setCost(cost);
        starListMapper.insert(starList);
        return "成功";
    }
    @GetMapping("/getstar/{userId}")
    public List getstar(@PathVariable("userId")int userId){
        QueryWrapper<StarList> wrapper=new QueryWrapper<>();
        wrapper
                .eq("user_id",userId);
        List<StarList> starLists=starListMapper.selectList(wrapper);
        return starLists;
    }
    @GetMapping("/show/{clo_type_id}/{store_id}")
    public Clothing show(@PathVariable("clo_type_id")int clo_type_id,@PathVariable("store_id")int store_id){
        String sava_path="localhost:8181\\img";
        String url=null;
        QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
        wrapper
                .eq("clo_type_id",clo_type_id)
                .eq("store_id",store_id)
                .eq("state","在库")
                .isNotNull("img_url")
                .groupBy("store_id","clo_type_id")
                .isNotNull("img_url");
        List<Clothing> clothingList = clothingMapper.selectList(wrapper);
        Clothing c=clothingList.get(0);
        url=c.getImgUrl();
        c.setImgUrl(sava_path+"\\"+url);
        return c;
    }
}

