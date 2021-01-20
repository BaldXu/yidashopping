package com.wuzhi.controlled;


import com.wuzhi.entity.AdminInfo;
import com.wuzhi.entity.CustomerInfo;
import com.wuzhi.mapper.AdminInfoMapper;
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
@RequestMapping("/admin-info")
public class AdminInfoController {
    @Autowired
    private AdminInfoMapper adminInfoMapper;

    @GetMapping("/find/findAll")
    public List findAll() {
        List<AdminInfo> adminInfos = adminInfoMapper.selectList(null);
        return adminInfos;
    }

    @GetMapping("/find/findById/{id}")
    public AdminInfo findById(@PathVariable("id") int id) {
        AdminInfo adminInfo = adminInfoMapper.selectById(id);
        return adminInfo;
    }
}

