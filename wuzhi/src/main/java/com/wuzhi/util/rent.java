package com.wuzhi.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhi.entity.*;
import com.wuzhi.mapper.*;

import java.util.Date;
import java.util.List;

public class rent {

    public String rent(int customerId,
                       int clo_type_id,
                       int num,
                       int storeId,
                       StockMapper stockMapper,
                       ClothingMapper clothingMapper,
                       StoreInfoMapper storeInfoMapper,
                       CustomerInfoMapper customerInfoMapper,
                       RentListMapper rentListMapper) {
        //刷新库存
        updateStock updateStock = new updateStock();
        updateStock.update(stockMapper, clothingMapper);

        //确定衣服余量
        QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",storeId);
        List<Clothing> clothingList = clothingMapper.selectList(wrapper);
        if(clothingList.size() == 0){
            return "商家不存在";
        }
        wrapper
                .eq("clo_type_id", clo_type_id)
                .eq("state", "在库");
        List<Clothing> clothingList2 = clothingMapper.selectList(wrapper);
        int surplus = clothingList2.size();
        if (surplus < num) {
            return "当前衣服余量不足，仅剩余:" + surplus;
        }
        //地址
        StoreInfo storeInfo = storeInfoMapper.selectById(storeId);
        String shipping_address = storeInfo.getAddress();
        CustomerInfo customerInfo = customerInfoMapper.selectById(customerId);
        String delivery_address = customerInfo.getAddress();
        if(shipping_address.isEmpty()){
            return "商户地址不存在";
        }
        if(delivery_address.isEmpty()){
            return "请去填写您的地址";
        }
        //第一次写入，并生成一个id
        RentList rentList = new RentList();
        rentList.setShippingAddress(shipping_address);
        rentList.setDeliveryAddress(delivery_address);
        rentList.setCloTypeId(clo_type_id);
        rentList.setNum(num);
        rentList.setCustomId(customerId);
        rentList.setStoreId(storeId);
        rentList.setTime(new Date());
        rentListMapper.insert(rentList);
        //获取生成的ID
        int rentListId = rentList.getId();
        int rent = 0;
        int deposit = 0;
        //选取前几件衣服并出租
        QueryWrapper<Clothing> wrapper1 = new QueryWrapper<>();
        wrapper1
                .eq("clo_type_id", clo_type_id)
                .last("limit " + num)
                .eq("state", "在库");
        List<Clothing> clothings = clothingMapper.selectList(wrapper1);
        for (Clothing clo : clothings) {
            clo.setState("出租");
            clo.setRentListId(rentListId);
            rent = rent + clo.getCost();
            deposit = deposit + clo.getDeposit();
            clothingMapper.updateById(clo);
        }
        //刷新库存
        updateStock.update(stockMapper, clothingMapper);

        rentList.setRent(rent);
        rentList.setDeposit(deposit);
        rentList.setState("未归还，未发货");
        rentListMapper.updateById(rentList);
        return "订单"+rentList.getId()+"已生成";
    }
    }

