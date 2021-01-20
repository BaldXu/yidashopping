package com.wuzhi.controlled;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhi.entity.*;
import com.wuzhi.mapper.*;
import com.wuzhi.util.rent;
import com.wuzhi.util.updateStock;
import lombok.Data;
import org.apache.ibatis.jdbc.Null;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
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
@RequestMapping("/rent-list")
public class RentListController {
    @Autowired
    private RentListMapper rentListMapper;
    @Autowired
    private ClothingMapper clothingMapper;
    @Autowired
    private StoreInfoMapper storeInfoMapper;
    @Autowired
    private CustomerInfoMapper customerInfoMapper;
    @Autowired
    private StockMapper stockMapper;

    @GetMapping("/find/findAll")
    public List findAll() {
        List<RentList> rentLists = rentListMapper.selectList(null);
        return rentLists;
    }

    @GetMapping("/find/findById/{id}")
    public RentList findById(@PathVariable("id") int id) {
        RentList rentList = rentListMapper.selectById(id);
        return rentList;
    }

    //返回当前用户的订单到期时间
    @GetMapping("/find/RentDueDate/{id}")
    public String DueDate(@PathVariable("id") int id) {
        RentList rentList = rentListMapper.selectById(id);
        java.text.Format formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = rentList.getTime();
        int deposit = rentList.getDeposit();
        int rent = rentList.getRent();
        int Days = deposit / rent;
        long afterTime = (date.getTime() / 1000) + 60 * 60 * 24 * Days;
        date.setTime(afterTime * 1000);
        String afterDate = formatter.format(date);
        return afterDate;
    }

    //根据购买者ID查询租赁表
    @GetMapping("/find/findRentListByCuId/{customerId}")
    public List findRentListByCuId(@PathVariable("customerId") int customerid) {
        QueryWrapper<RentList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("custom_id", customerid);
        List<RentList> rentLists = rentListMapper.selectList(wrapper);
        return rentLists;
    }
    //根据购买者ID查询租赁表
    @GetMapping("/find/findThisRentList/{customerId}")
    public List findThisRentList(@PathVariable("customerId") int customerid) {
//        Subject subject= SecurityUtils.getSubject();
//        User currentUser =(User) subject.getPrincipal();//拿到user对象
//        int customerid=currentUser.getId();
        QueryWrapper<RentList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("custom_id", customerid);
        List<RentList> rentLists = rentListMapper.selectList(wrapper);
        return rentLists;
    }
    //查询当前商户的租赁表
    @GetMapping("/find/findThisStRentList")
        public List findThisStRentList() {
        //获取当前用户id
        UserController userController = new UserController();
        int storeId=userController.showUserId();
        QueryWrapper<RentList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id", storeId);
        List<RentList> rentLists = rentListMapper.selectList(wrapper);
        return rentLists;
    }
    //根据商户id查询租赁表
    @GetMapping("/find/findRentListByStId/{storeId}")
    public List findRentListByStId(@PathVariable("storeId") int storeid) {
        QueryWrapper<RentList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id", storeid);
        List<RentList> rentLists = rentListMapper.selectList(wrapper);
        return rentLists;
    }

    //租出去(生成订单
    @RequestMapping("/rent/{clo_type_id}/{num}/{storeId}/{customerId}")
    @ResponseBody
    public String rent(
                       @PathVariable("clo_type_id") int clo_type_id,
                       @PathVariable("num") int num,
                       @PathVariable("storeId") int storeId,
                       @PathVariable("customerId") int customerid) {
        //刷新库存
        updateStock updateStock = new updateStock();
        updateStock.update(stockMapper, clothingMapper);
        rent rent1 =new rent();
//        Subject subject= SecurityUtils.getSubject();
//        User currentUser =(User) subject.getPrincipal();//拿到user对象
//        int customerId=currentUser.getId();
        String result = rent1.rent(customerid,clo_type_id,num,storeId,stockMapper,clothingMapper,storeInfoMapper,customerInfoMapper,rentListMapper);

        return result;
    }

    //商家进行归还确认
    @PutMapping("/reclothing/{id}/{storeId}")
    public String reclothing(@PathVariable("id") int id,
                             @PathVariable("storeId") int storeId) {
        //刷新库存
        updateStock updateStock = new updateStock();
        updateStock.update(stockMapper, clothingMapper);
        RentList rentList = rentListMapper.selectById(id);
        QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
        wrapper
                .eq("rent_list_id", rentList.getId());
        List<Clothing> clothingList = clothingMapper.selectList(wrapper);
        if(rentList.getStoreId()!=storeId){
            return "这不是你的订单";
        }
        if (rentList.getState().equals("未归还，未发货")) {
            return "归还失败，商品还没发货";
        }
        if(rentList.getState().equals("已归还")){
            return "商品已归还，无需再归还";
        }
        if(rentList.getState().equals("未归还，已发货")) {
            for (Clothing clo : clothingList) {
                clo.setState("在库");
                clo.setRentListId(0);
                clothingMapper.updateById(clo);
            }
            //刷新库存
            updateStock.update(stockMapper, clothingMapper);
            //计算时间
            int amoney=0;
            Date smData=rentList.getTime();
            Date bigData=new Date();
            Calendar cal = Calendar.getInstance();
            //处理
            cal.setTime(smData);
            long time1=cal.getTimeInMillis();
            cal.setTime(bigData);
            long time2=cal.getTimeInMillis();
            long betweenTime=(time2-time1)/(1000*3600*24);
            int bwtTime= Integer.parseInt(String.valueOf(betweenTime));
            amoney=bwtTime*rentList.getRent();
            rentList.setRent(amoney);
            rentList.setDeposit(0);
            rentList.setState("已归还");
            rentList.setRetime(new Date());
            rentListMapper.updateById(rentList);
            return "商品归还成功";
        }
        else {
            return "商品状态错误";
        }
    }
    //获取商家的总租金额
    @GetMapping("/getThisStoreAllRent/{store_id}")
    public int getThisStoreAllRent(@PathVariable("store_id")int store_id){
        int amoney=0;
        QueryWrapper<RentList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",store_id)
                .eq("state","已归还");
        List<RentList> rentLists= rentListMapper.selectList(wrapper);
        for (RentList r:rentLists){
            amoney=amoney+r.getRent();
        }
        return amoney;
    }
    //获取全平台的总租金额
    @GetMapping("/getAllRent")
    public int getAllRent(){
        int amoney=0;
        QueryWrapper<RentList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("state","已归还");
        List<RentList> rentLists= rentListMapper.selectList(wrapper);
        for (RentList r:rentLists){
            amoney=amoney+r.getRent();
        }
        return amoney;
    }
    //查询已归还订单
    @GetMapping("/showRentListRe/{storeId}")
    public List showRentListRe(@PathVariable("storeId")int storeId) {
        QueryWrapper<RentList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",storeId)
                .eq("state","已归还");
        List<RentList> rentLists = rentListMapper.selectList(wrapper);
        return rentLists;
    }
    //查询未归还订单
    @GetMapping("/showRentListNotRe/{storeId}")
    public List showRentListNotRe(@PathVariable("storeId")int storeId) {
        QueryWrapper<RentList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",storeId)
                .like("state","未归还，已发货");

        List<RentList> rentLists = rentListMapper.selectList(wrapper);
        return rentLists;
    }
    //查询未归还未发货订单
    @GetMapping("/showRentListNotSend/{storeId}")
    public List showRentListNotSend(@PathVariable("storeId")int storeId){
        QueryWrapper<RentList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",storeId)
                .eq("state","未归还，未发货");
        List<RentList> rentLists = rentListMapper.selectList(wrapper);
        return rentLists;
    }

    //发货
    @PutMapping("/send/{id}/{storeId}")
    public String send(@PathVariable("id")int id,@PathVariable("storeId")int storeId){
        RentList rentList = rentListMapper.selectById(id);
        //获取当前用户id
        if(!rentList.getStoreId().equals(storeId)){
            return "这不是你的订单";
        }
        if(rentList.getState().equals("未归还，已发货"))
        {
            return "商品已发货";
        }
        if(rentList.getState().equals("已归还"))
        {
            return "商品已归还，无需发货";
        }
        if(rentList.getState().equals("未归还，未发货")) {
            rentList.setState("未归还，已发货");
            rentListMapper.updateById(rentList);
            return "发货成功";
        }
        else {
            return "商品状态错误";
        }
    }

    //取消租货订单
    @DeleteMapping("/cancelRentList/{id}/{storeId}")
    public String cancelRentList(@PathVariable("id")int id,@PathVariable("storeId")int storeId){
        RentList rentList = rentListMapper.selectById(id);
        if(!rentList.getStoreId().equals(storeId)){
            return "这不是你的订单";
        }
        QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
        wrapper
                .eq("rent_list_id", rentList.getId());
        List<Clothing> clothingList = clothingMapper.selectList(wrapper);

        if(rentList.getState().equals("未归还，已发货")){
            return "商品已发货，取消失败";
        }
        if(rentList.getState().equals("已归还")){
            return "商品已归还，取消失败";
        }
        if (rentList.getState().equals("未归还，未发货")) {
            for (Clothing clo : clothingList) {
                clo.setState("在库");
                clo.setRentListId(0);
                clothingMapper.updateById(clo);
            }
                //刷新库存
                updateStock updateStock = new updateStock();
                updateStock.update(stockMapper, clothingMapper);

                rentListMapper.deleteById(rentList);
            return "取消成功";
        }
        return "商品状态错误";
    }


}

