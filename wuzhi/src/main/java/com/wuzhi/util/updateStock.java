package com.wuzhi.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhi.entity.Clothing;
import com.wuzhi.entity.Stock;
import com.wuzhi.mapper.ClothingMapper;
import com.wuzhi.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class
updateStock {
    public void update(StockMapper stockMapper,ClothingMapper clothingMapper){
        List<Stock> stocks=stockMapper.selectList(null);
        for (Stock st:stocks) {
            QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
            wrapper
                    .eq("clo_type_id",st.getCloTypeId())
                    .eq("state","在库");
            List<Clothing> clothing= clothingMapper.selectList(wrapper);
            st.setNum(clothing.size());
            stockMapper.updateById(st);
        }
    }
    public void addStock(StockMapper stockMapper,ClothingMapper clothingMapper){
        QueryWrapper<Clothing> wrapper =new QueryWrapper<>();
        wrapper
                .select("distinct clo_type_id");
        List<Object> clothingList =clothingMapper.selectObjs(wrapper);
        QueryWrapper<Stock> wrapper2 =new QueryWrapper<>();
        wrapper2
                .select("distinct clo_type_id");
        List<Object> stockList =stockMapper.selectObjs(wrapper2);
        clothingList.removeAll(stockList);
        int cloTypeId;

        if (clothingList.size()>0){
            for (Object i:clothingList) {
                cloTypeId=Integer.parseInt(i.toString());
//                System.out.println("cloTypeId          "+cloTypeId);
                Stock stock1 = new Stock();
                stock1.setCloTypeId(cloTypeId);
                stockMapper.insert(stock1);
            }
            update(stockMapper,clothingMapper);
        }


    }
}
