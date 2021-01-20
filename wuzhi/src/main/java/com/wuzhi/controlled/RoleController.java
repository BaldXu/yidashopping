package com.wuzhi.controlled;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhi.entity.*;
import com.wuzhi.mapper.*;
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
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StoreInfoMapper storeInfoMapper;
    @Autowired
    private ClothingMapper clothingMapper;
    @Autowired
    private CloImgMapper cloImgMapper;

    @GetMapping("/find/findAll")
    public List findAll(){
        List<Role> roles=roleMapper.selectList(null);
        return roles;
    }
    @GetMapping("/find/findById/{id}")
    public Role findById(@PathVariable("id")int id){
        Role role = roleMapper.selectById(id);
        return role;
    }
    //用户注册为商家
    @PutMapping("/update/registerStore")
    public String registerStore(){
        UserController userController = new UserController();
        int thisUserId=userController.showUserId();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id",thisUserId);
        List<User> users = userMapper.selectList(wrapper);
        User user = users.get(0);
        if(user.getRoleId() == 2){
            return "当前已为商家，无需注册";
        }
        if (user.getRoleId() == 3){
            return "你是管理员";
        }
        user.setRoleId(2);
        userMapper.updateById(user);
        return "成功注册为商户";
    }
    //商户注销
    @DeleteMapping("/delete/deleteStore")
    public String deleteStore(){
        UserController userController = new UserController();
        int thisUserId=userController.showUserId();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id",thisUserId);
        List<User> users = userMapper.selectList(wrapper);
        User user = users.get(0);
        if(user.getRoleId() == 1){
            return "你是用户";
        }
        if (user.getRoleId() == 3){
            return "你是管理员";
        }
        QueryWrapper<StoreInfo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("id",thisUserId);
        storeInfoMapper.delete(wrapper2);
        QueryWrapper<Clothing> wrapper3 = new QueryWrapper<>();
        wrapper3.eq("store_id",thisUserId);
        clothingMapper.delete(wrapper3);
        QueryWrapper<CloImg> wrapper4 = new QueryWrapper<>();
        wrapper4.eq("store_id",thisUserId);
        cloImgMapper.delete(wrapper4);
        user.setRoleId(1);
        userMapper.updateById(user);
        return "注销成功";
    }

}

