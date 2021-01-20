package com.wuzhi.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhi.entity.RentList;
import com.wuzhi.entity.SellList;
import com.wuzhi.mapper.SellListMapper;

import java.util.List;

public class Statistcs {
    public String quarterSellStatistcs(int storeId,
                                   int year,
                                   String quarter,
                                   SellListMapper sellListMapper){
        int Smonth=0;
        int Emonth=0;
        int Eyear=0;
        if(quarter.equals("春季")) {
            Smonth=3;
            Emonth=5;
            Eyear = year;
        }
        else if(quarter.equals("夏季")){
            Smonth=6;
            Emonth=8;
            Eyear = year;
        }
        else if(quarter.equals("秋季")){
            Smonth=9;
            Emonth=11;
            Eyear = year;
        }
        else if(quarter.equals("冬季")){
            Smonth=12;
            Emonth=12;
            Eyear = year+1;
        }
        else return "季度不存在";
        String monthStart=year+"-"+Smonth+"-01";
        String monthEnd=year+"-"+Emonth+"-30";
        QueryWrapper<SellList> wrapper = new QueryWrapper<>();
        if(Eyear==year)
        wrapper
                .eq("store_id",storeId)
                .between("time",monthStart,monthEnd);
        if(Eyear==year+1){
            Smonth = 1;
            Emonth = 2;
            String NmonthStart=Eyear+"-"+Smonth+"-01";
            String NmonthEnd=Eyear+"-"+Emonth+"-28";
            if(Eyear%4==0){
                NmonthEnd=Eyear+"-"+Emonth+"-29";
            }
            wrapper
                    .eq("store_id",storeId)
                    .between("time",monthStart,monthEnd)
                    .or().between("time",NmonthStart,NmonthEnd);
        }
        List<SellList> sellLists = sellListMapper.selectList(wrapper);
        int cost = 0;
        int num = 0;
        for(SellList sl:sellLists){
            cost = cost+sl.getAmoney();
            num = num +sl.getNum();

        }
        return year+quarter+"出售资金统计为:"+cost+"营销统计为:"+num;
    }
}
