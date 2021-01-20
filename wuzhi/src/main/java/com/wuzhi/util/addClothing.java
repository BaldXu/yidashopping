package com.wuzhi.util;

import com.wuzhi.entity.CloType;
import com.wuzhi.entity.Clothing;
import com.wuzhi.mapper.CloTypeMapper;
import com.wuzhi.mapper.ClothingMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

public class addClothing {
    public String add(CloTypeMapper cloTypeMapper,ClothingMapper clothingMapper,String name,int deposit,int rent,int cost,int store_id,int clo_type_id,int num){
        Clothing clo=new Clothing();
        clo.setName(name);
        clo.setState("在库");
        clo.setCloTypeId(clo_type_id);
        clo.setCost(cost);
        clo.setRent(rent);
        clo.setDeposit(deposit);
        clo.setStoreId(store_id);
        for (int i=0;i<num;i++){
            clothingMapper.insert(clo);
        }
        //更新服装类型
        HashMap<String, Object> map = new HashMap<>();
        //自定义查询条件
        map.put("id",clo_type_id);
        List<CloType> users = cloTypeMapper.selectByMap(map);
        if (users.size()==0){
            CloType cloType = new CloType();
            cloType.setId(clo_type_id);
            cloTypeMapper.insert(cloType);
            return "该服装编号不存在";
        }
        return "yes";
    }


}
