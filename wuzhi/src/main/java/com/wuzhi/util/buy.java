package com.wuzhi.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhi.entity.*;
import com.wuzhi.mapper.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

public class buy {

    public String buy(int customerId,
                      int clo_type_id,
                      int num,
                      int storeId,
                      StockMapper stockMapper,
                      ClothingMapper clothingMapper,
                      StoreInfoMapper storeInfoMapper,
                      CustomerInfoMapper customerInfoMapper,
                      SellListMapper sellListMapper){
        //确定衣服余量
        QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",storeId)
                .eq("clo_type_id",clo_type_id);
        List<Clothing> clothing= clothingMapper.selectList(wrapper);
        int surplus=clothing.size();
        if (surplus<num){
            return "当前衣服余量不足，仅剩余:"+surplus;
        }
        //地址
        StoreInfo storeInfo = storeInfoMapper.selectById(storeId);
        String shipping_address = storeInfo.getAddress();
        CustomerInfo customerInfo = customerInfoMapper.selectById(customerId);
        String delivery_address = customerInfo.getAddress();
        System.out.println("3     "+shipping_address);
        if(shipping_address.isEmpty()){
            return "商户地址不存在";
        }
        if(delivery_address.isEmpty()){
            return "请去填写您的地址";
        }
        //第一次写入，并生成一个id
        SellList sellList=new SellList();
        sellList.setShippingAddress(shipping_address);
        sellList.setDeliveryAddress(delivery_address);
        sellList.setCloTypeId(clo_type_id);
        sellList.setNum(num);
        sellList.setCustomerId(customerId);
        sellList.setStoreId(storeId);
        sellList.setTime(new Date());
        sellList.setState("未发货");
        sellListMapper.insert(sellList);
        System.out.println(sellList);
        //获取生成的ID
        int sellListId=sellList.getId();
        int cost=0;
        //选取前几件衣服并出售
        QueryWrapper<Clothing> wrapper1 = new QueryWrapper<>();
        wrapper1
                .eq("clo_type_id",clo_type_id)
                .last("limit "+num)
                .eq("state","在库");
        List<Clothing> clothings= clothingMapper.selectList(wrapper1);
        for (Clothing clo:clothings) {
            clo.setState("已出售");
            clo.setSellListId(sellListId);
            cost=cost+clo.getCost();
            clothingMapper.updateById(clo);
        }
        //刷新库存
        updateStock updateStock = new updateStock();
        updateStock.update(stockMapper,clothingMapper);
        //写入
        sellList.setAmoney(cost);
        sellListMapper.updateById(sellList);
        return "yes,"+sellList.getId();
    }

    public String canItBuy(int num,int clo_type_id,int storeId,StockMapper stockMapper,ClothingMapper clothingMapper){
        //刷新库存
        updateStock updateStock = new updateStock();
        updateStock.update(stockMapper,clothingMapper);
        //确定衣服余量
        QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",storeId);
        List<Clothing> clothingList = clothingMapper.selectList(wrapper);
        if(clothingList.size() == 0){
            return "商家不存在";
        }
        wrapper
                .eq("clo_type_id",clo_type_id)
                .eq("state", "在库");
        List<Clothing> clothing= clothingMapper.selectList(wrapper);
        int surplus=clothing.size();
        if (surplus<num){
            return "当前衣服余量不足，仅剩余:"+surplus;
        }else {
            return "够了";
        }
    }
}
