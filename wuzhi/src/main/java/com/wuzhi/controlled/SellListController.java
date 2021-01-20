package com.wuzhi.controlled;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhi.entity.*;
import com.wuzhi.mapper.*;
import com.wuzhi.util.buy;
import com.wuzhi.util.updateStock;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2020-12-01
 */
@RestController
@CrossOrigin
@RequestMapping("/sell-list")
public class SellListController {
    @Autowired
    private SellListMapper sellListMapper;
    @Autowired
    private CustomerInfoMapper customerInfoMapper;
    @Autowired
    private StoreInfoMapper storeInfoMapper;
    @Autowired
    private ClothingMapper clothingMapper;
    @Autowired
    private StockMapper stockMapper;

    @GetMapping("/find/findAll")
    public List findAll(){
        List<SellList> sellLists=sellListMapper.selectList(null);
        return sellLists;
    }
    @GetMapping("/find/findById/{id}")
    public SellList findById(@PathVariable("id")int id){
        SellList sellList = sellListMapper.selectById(id);
        return sellList;
    }

    //生成出售表（未购买
    @RequestMapping("/buy/{clo_type_id}/{num}/{storeId}/{customerId}")
    @ResponseBody
    public String buy(@PathVariable("clo_type_id")int clo_type_id,
                      @PathVariable("num")int num,
                      @PathVariable("storeId")int storeId,
                      @PathVariable("customerId")int customerId){
        //刷新库存
        updateStock updateStock = new updateStock();
        updateStock.update(stockMapper,clothingMapper);
        //确定衣服余量
        buy buy1 = new buy();
        String result1=buy1.canItBuy(num,clo_type_id,storeId,stockMapper,clothingMapper);
        if (result1!="够了"){
            return result1;
        }
        //第一次写入，并生成一个id
        String resulut2=buy1.buy(customerId,clo_type_id,num,storeId,stockMapper,clothingMapper,storeInfoMapper,customerInfoMapper,sellListMapper);
        return resulut2;
    }

    //根据购买者ID查询出售表
    @GetMapping("/find/findCustomerSellById/{customerId}")
    public List<SellList> findCustomerSellById(@PathVariable("customerId")int customerid){
        QueryWrapper<SellList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("customer_id",customerid);
        List<SellList> sellLists=sellListMapper.selectList(wrapper);
        return sellLists;
    }
    //根据当前用户查询出售表
    @GetMapping("/find/findThisCustomerSell/{customerid}")
    public List<SellList> findThisCustomerSell(@PathVariable("customerid") int customerid){
        QueryWrapper<SellList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("customer_id",customerid);
        List<SellList> sellLists=sellListMapper.selectList(wrapper);
        return sellLists;
    }
    //根据商户查询出售表
    @GetMapping("/find/StoreSellfindById/{storeId}")
    public List findStoreSellById(@PathVariable("storeId")int storeid){
        QueryWrapper<SellList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",storeid);
        List<SellList> sellLists = sellListMapper.selectList(wrapper);
        return sellLists;
    }
    //根据商户查询未发货
    @GetMapping("/find/thisStoreNotDelivered/{storeId}")
    public List thisStoreNotDelivered(@PathVariable("storeId")int storeid){
        QueryWrapper<SellList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",storeid)
                .eq("state","未发货");
        List<SellList> sellLists = sellListMapper.selectList(wrapper);
        return sellLists;
    }
    //根据商户查询已发货
    @GetMapping("/find/thisStoreDelivered/{storeId}")
    public List thisStoreDelivered(@PathVariable("storeId")int storeid){
        QueryWrapper<SellList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",storeid)
                .eq("state","已发货");
        List<SellList> sellLists = sellListMapper.selectList(wrapper);
        return sellLists;
    }
    //查询未发货的表单
    @GetMapping("/find/findNotDelivered")
    public List<SellList> findNotDelivered(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("state","未发货");
        List<SellList> sellLists = sellListMapper.selectByMap(map);
        return sellLists;
    }
    //查询已发货发货的表单
    @GetMapping("/find/findDelivered")
    public List<SellList> findDelivered(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("state","已发货");
        List<SellList> sellLists = sellListMapper.selectByMap(map);
        return sellLists;
    }
    //商户发货
    @PutMapping("/find/selldeliverGoods/{sellListId}")
    public String selldeliverGoods(@PathVariable("sellListId")int sellListId){
        SellList sellList= sellListMapper.selectById(sellListId);
        sellList.setState("已发货");
        sellListMapper.updateById(sellList);
        return "编号： "+sellList.getId()+" 的订单已发货";
    }
    //获取该商户赚的所有钱
    @GetMapping("/find/getThisStoreAmoney/{storeId}")
    public int getAllMoney(@PathVariable("storeId")int storeId){
        int amoney=0;
        QueryWrapper<SellList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",storeId)
                .eq("state","已发货");
        List<SellList> sellLists= sellListMapper.selectList(wrapper);
        for (SellList s:sellLists){
            amoney=amoney+s.getAmoney();
        }
        return amoney;
    }
    //获取所有钱
    @GetMapping("/find/getAllAmoney")
    public int getAllMoney(){
        int amoney=0;
        QueryWrapper<SellList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("state","已发货");
        List<SellList> sellLists= sellListMapper.selectList(wrapper);
        for (SellList s:sellLists){
            amoney=amoney+s.getAmoney();
        }
        return amoney;
    }


}

