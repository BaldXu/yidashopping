package com.wuzhi.controlled;


import com.wuzhi.entity.*;
import com.wuzhi.mapper.*;
import com.wuzhi.util.buy;
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
 * @since 2020-12-02
 */
@RestController
@CrossOrigin
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private ClothingMapper clothingMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private StoreInfoMapper storeInfoMapper;
    @Autowired
    private CustomerInfoMapper customerInfoMapper;
    @Autowired
    private SellListMapper sellListMapper;
    //返回当前用户的购物车
    @GetMapping("/showShoppingCart/{userId}")
    public List<ShoppingCart> showShoppingCart(@PathVariable("userId")int userId){
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id",userId);
        List<ShoppingCart> thisUserShoppingCart = shoppingCartMapper.selectByMap(map);
        if (thisUserShoppingCart.size()==0){
            return null;
        }
        return thisUserShoppingCart;
    }
    //购物车添加内容
    @PostMapping("/add/{clo_type_id}/{num}/{storeId}/{userId}")
    public String add(@PathVariable("clo_type_id")int clo_type_id, @PathVariable("num")int num, @PathVariable("storeId")int storeId, @PathVariable("userId")int userId){
        //先判断还够不够
        buy buy = new buy();
        String result=buy.canItBuy(num,clo_type_id,storeId,stockMapper,clothingMapper);
        if (result!="够了"){return "衣服数量不足！";}
        //添加
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        shoppingCart.setCloTypeId(clo_type_id);
        shoppingCart.setNum(num);
        shoppingCart.setStoreId(storeId);
        //根据商户ID和服装编号查出单价和总价
        HashMap<String, Object> map = new HashMap<>();
        map.put("store_id",storeId);
        map.put("clo_type_id",clo_type_id);
        List<Clothing> clothing = clothingMapper.selectByMap(map);
        int cost=clothing.get(0).getCost();
        int amoney=cost*num;
        shoppingCart.setCost(cost);
        shoppingCart.setAmoney(amoney);
        shoppingCartMapper.insert(shoppingCart);
        return "成功";
    }
    //购物车一键购买
    @PostMapping("/buy/{userId}")
    public String buy(@PathVariable("userId")int userId){
        //查询当前用户的所有内容
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id",userId);
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectByMap(map);
        //准备购买(先判断库存是否足够
        buy buy1 = new buy();
        SellListController sellListController = new SellListController();
        for (ShoppingCart sc:shoppingCarts){
            String resule=buy1.canItBuy(sc.getNum(),sc.getCloTypeId(),sc.getStoreId(),stockMapper,clothingMapper);
            if (resule!="够了"){
                return "购物车表单编号为 "+sc.getId()+" 的服装库存不足！";
            }
        }
        for (ShoppingCart sc:shoppingCarts){//遍历购物车进行购买
            buy1.buy(sc.getUserId(),sc.getCloTypeId(),sc.getNum(),sc.getStoreId(),stockMapper,clothingMapper,storeInfoMapper,customerInfoMapper,sellListMapper);
//            //写出售表
//            SellList sellList = new SellList();
//            sellList.setAmoney(sc.getAmoney());
//            sellList.setState("未发货");
//            sellList.setTime(new Date());
//            sellList.setStoreId(sc.getStoreId());
//            sellList.setCloTypeId(sc.getCloTypeId());
//            sellList.setCustomerId(sc.getUserId());
//            sellList.setNum(sc.getNum());
//            //查找发货地址和收货地址
//            StoreInfo storeInfo=storeInfoMapper.selectById(sc.getStoreId());
//            sellList.setShippingAddress(storeInfo.getAddress());
//            CustomerInfo customerInfo=customerInfoMapper.selectById(sc.getUserId());
//            sellList.setDeliveryAddress(customerInfo.getAddress());
//            int resultSell=sellListMapper.insert(sellList);
            int resultDel =shoppingCartMapper.deleteByMap(map);
            System.out.println("总共删除了"+resultDel+"条");
        }
        return "购买成功！";
    }
    //购物车删除
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id")int id){
        //查询当前用户的所有内容
        int result=shoppingCartMapper.deleteById(id);
        if (result!=0){
            return "删除成功！";
        }else {
            return "失败！！找不到该购物车！";
        }
    }
}

