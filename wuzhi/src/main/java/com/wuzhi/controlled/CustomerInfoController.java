package com.wuzhi.controlled;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhi.entity.CustomerInfo;
import com.wuzhi.entity.StoreInfo;
import com.wuzhi.entity.User;
import com.wuzhi.mapper.CustomerInfoMapper;
import com.wuzhi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/customer-info")
public class CustomerInfoController {
    @Autowired
    private CustomerInfoMapper customerInfoMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/find/findAll")
    public List findAll() {
        List<CustomerInfo> customerInfos = customerInfoMapper.selectList(null);
        return customerInfos;
    }

    @GetMapping("/find/findById/{id}")
    public CustomerInfo findById(@PathVariable("id") int id) {
        CustomerInfo customerInfo = customerInfoMapper.selectById(id);
        return customerInfo;
    }
    //合并填写修改表内容
    @PostMapping("/addById/{address}/{thisUserId}")
    public String addById(@PathVariable("address")String address,@PathVariable("thisUserId")int thisUserId){
        QueryWrapper<CustomerInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",thisUserId);
        List<CustomerInfo> customerInfos = customerInfoMapper.selectList(wrapper);
        if(customerInfos.size()!=0){
            CustomerInfo customerInfo = customerInfoMapper.selectById(thisUserId);
            customerInfo.setAddress(address);
            customerInfoMapper.updateById(customerInfo);
            return "修改成功";
        }
        else if(customerInfos.size()==0) {
            CustomerInfo customerInfo = new CustomerInfo();
            customerInfo.setUserId(thisUserId);
            customerInfo.setAddress(address);
            customerInfoMapper.insert(customerInfo);
            return "填写完成";
        }
        return "执行错误";
    }
//    //地址更新服务
//    @PutMapping("/updateById/{address}")
//    public String updateById(@PathVariable("address")String address){
//        UserController userController = new UserController();
//        int thisUserId=userController.showUserId();
//        QueryWrapper<CustomerInfo> wrapper = new QueryWrapper<>();
//        wrapper.eq("user_id",thisUserId);
//        List<CustomerInfo> customerInfos = customerInfoMapper.selectList(wrapper);
//        if(customerInfos.size()==0){
//            return "用户不存在，请先填写用户信息";
//        }
//        CustomerInfo customerInfo = customerInfoMapper.selectById(thisUserId);
//        customerInfo.setAddress(address);
//        customerInfoMapper.updateById(customerInfo);
//        return "修改成功";
//    }
    //根据邮箱查找用户id
    @GetMapping("/find/findByName/{email}")
    public String findByName(@PathVariable("email")String email){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .eq("role_id",1)
                .eq("email",email)
                .select("id,nickname,email");
        List<User> users = userMapper.selectList(wrapper);
        if(users.size() == 0){
            return "用户不存在";
        }
        return users.get(0).toString();
    }
}

