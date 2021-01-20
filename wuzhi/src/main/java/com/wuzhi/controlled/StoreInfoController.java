package com.wuzhi.controlled;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhi.entity.*;
import com.wuzhi.mapper.*;
import com.wuzhi.util.Statistcs;
import com.wuzhi.mapper.ClothingMapper;
import com.wuzhi.mapper.StockMapper;
import com.wuzhi.mapper.StoreInfoMapper;
import com.wuzhi.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
 * @since 2020-11-18
 */
@RestController
@CrossOrigin
@RequestMapping("/store-info")
public class StoreInfoController {
    @Autowired
    private StoreInfoMapper storeInfoMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private ClothingMapper clothingMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SellListMapper sellListMapper;
    @Autowired
    private RentListMapper rentListMapper;

    @GetMapping("/find/findAll")
    public List findAll(){
        List<StoreInfo> storeInfos=storeInfoMapper.selectList(null);
        return storeInfos;
    }
    @GetMapping("/find/findById/{id}")
    public StoreInfo findById(@PathVariable("id")int id){
        StoreInfo storeInfo = storeInfoMapper.selectById(id);
        return storeInfo;
    }
    //合并填写修改表内容
    @PostMapping("/addById/{address}/{storeId}")
    public String addById(@PathVariable("address")String address,@PathVariable("storeId")int storeId){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",storeId);
        List<StoreInfo> storeInfos = storeInfoMapper.selectByMap(map);
        if(storeInfos.size() != 0 ){
            StoreInfo storeInfo = storeInfoMapper.selectById(storeId);
            storeInfo.setAddress(address);
            storeInfoMapper.updateById(storeInfo);
            return "修改成功";
        }
        if(storeInfos.size() == 0 ) {
            StoreInfo storeInfo = new StoreInfo();
            storeInfo.setId(storeId);
            storeInfo.setAddress(address);
            storeInfoMapper.insert(storeInfo);
            return "填写成功";
        }
        else {
            return "执行错误";
        }
    }
//    //地址更新服务
//    @PutMapping("/updateById/{address}")
//    public String updateById(@PathVariable("address")String address){
//        UserController userController = new UserController();
//        int thisUserId=userController.showUserId();
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id",thisUserId);
//        List<StoreInfo> storeInfos = storeInfoMapper.selectByMap(map);
//        if(storeInfos.size() == 0 ){
//            return "商户不存在，请先填写商户信息";
//        }
//        StoreInfo storeInfo = storeInfoMapper.selectById(thisUserId);
//        storeInfo.setAddress(address);
//        storeInfoMapper.updateById(storeInfo);
//        return "修改成功";
//    }

    //根据商户的ID，然后返回查询出的衣服的clo_type_id（要求不重复）
    @GetMapping("/findCloTypeById/{id}")
    public List findCloTypeById(@PathVariable("id")int storeid){
        QueryWrapper<Clothing> wrapper =new QueryWrapper<>();
        wrapper
                .eq("store_id",storeid)
                .select("distinct store_id,clo_type_id");
        List<Clothing> clothings =clothingMapper.selectList(wrapper);
        return clothings;
    }
    //查找商家
    @GetMapping("/find/findIdByStoreName/{storeName}")
    public List<User> findIdByStoreName(@PathVariable("storeName")String storeName){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .eq("role_id",2)
                .like("nickname",storeName)
                .select("id,nickname");
        List<User> users = userMapper.selectList(wrapper);
        return users;
    }
    //出售表日统计（具体到天）
    @GetMapping("/daySellStatistcs/{day}/{storeId}")
    public String daySellStatistcs(@PathVariable("day") String day,
                                   @PathVariable("storeId")int storeId){
        String[] time =day.split("-");
        if(time.length!=3||time[0].isEmpty()||time[1].isEmpty()){
            return "日期错误，请选择年月日";
        }
        String dayStart = day+" 00:00:00";
        String dayEnd = day+" 23:59:59";
        QueryWrapper<SellList> wrapper = new QueryWrapper<>();
        wrapper
            .eq("store_id",storeId)
            .between("time",dayStart,dayEnd);
        List<SellList> sellLists = sellListMapper.selectList(wrapper);
        int cost = 0;
        int num = 0;
        for(SellList sl:sellLists){
        cost = cost+sl.getAmoney();
        num = num +sl.getNum();
    }
        return day+" 资金统计为:"+cost+" 营销统计为:"+num;

    }
    //出售表月统计（具体到月）
    @GetMapping("/monthSellStatistcs/{month}/{storeId}")
    public String monthSellStatistcs(@PathVariable("month") String month,
                                     @PathVariable("storeId")int storeId){
        String[] time =month.split("-");
         int length=month.split("-").length;
         if(length!=2){
             return "请重新选择年月";
         }
        String year = time[0];
        if(year.isEmpty()){
            return "请选择年份";
        }
        String monthStart=year+"-"+time[1]+"-01";
        String monthEnd=year+"-"+time[1]+"-30";
        QueryWrapper<SellList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",storeId)
                .between("time",monthStart,monthEnd);
        List<SellList> sellLists = sellListMapper.selectList(wrapper);
        int cost = 0;
        int num = 0;
        for(SellList sl:sellLists){
            cost = cost+sl.getAmoney();
            num = num +sl.getNum();

        }
        return month+" 资金统计为:"+cost+" 营销统计为:"+num;
    }
    //出售表季度统计(具体到年和季度，春季（3-5）、夏季（6-8）、秋季（9-11）、冬季（12-2）)
    @GetMapping("/quarterSellStatistcs/{year}/{quarter}/{storeId}")
    public String quarterSellStatistcs(@PathVariable("year") int year,
                                       @PathVariable("quarter") String quarter,
                                       @PathVariable("storeId")int storeId){
        Statistcs statistcs =new Statistcs();
        String result = statistcs.quarterSellStatistcs(storeId,year,quarter,sellListMapper);
        return result;
    }
    //出租表日统计
    @GetMapping("/dayRentStatistcs/{storeId}/{day}/{state}")
    public List dayRentStatistcs(@PathVariable("storeId")int storeId,
                                   @PathVariable("day") String day,
                                   @PathVariable("state")String state){
        String[] time =day.split("-");
        if(time.length!=3||time[0].isEmpty()||time[1].isEmpty()){
            return null;
        }
        String dayStart = day+" 00:00:00";
        String dayEnd = day+" 23:59:59";
        QueryWrapper<RentList> wrapper = new QueryWrapper<>();
        if(state.equals("所有")) {
            wrapper
                    .eq("store_id", storeId)
                    .between("time", dayStart, dayEnd);
        }
        else if(state.equals("已归还")){
            wrapper
                    .eq("store_id", storeId)
                    .eq("state",state)
                    .between("retime", dayStart, dayEnd);
        }else {
//            return "查询失败，请输入查询条件所有或者已归还";
        }
        List<RentList> rentLists = rentListMapper.selectList(wrapper);
        return rentLists;
    }
}

