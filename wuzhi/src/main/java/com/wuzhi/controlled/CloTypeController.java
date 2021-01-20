package com.wuzhi.controlled;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhi.entity.CloType;
import com.wuzhi.mapper.CloTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2020-11-24
 */
@RestController
@CrossOrigin
@RequestMapping("/clo-type")
public class CloTypeController {
    @Autowired
    private CloTypeMapper cloTypeMapper;
    //根据ID修改衣服类型的描述
    @PutMapping("/update/updateCloTypeById/{clo_type_id}/{type}")
    public String updateCloTypeById(@PathVariable("clo_type_id")int id,
                                    @PathVariable("type")String type){
        CloType cloType =cloTypeMapper.selectById(id);
        cloType.setType(type);
        cloTypeMapper.updateById(cloType);
        return "修改成功";
    }
    //模糊查询：根据type进行模糊查询
    @GetMapping("/find/findCloByType/{type}")
    public List findCloByType(@PathVariable("type")String type){
        QueryWrapper<CloType> wrapper = new QueryWrapper<>();
        wrapper.like("type",type);
        List<CloType> clos = cloTypeMapper.selectList(wrapper);
        return  clos;
    }
    //findById
    @GetMapping("/find/findCloTypeById/{clo_type_id}")
    public CloType findCloTypeById(@PathVariable("clo_type_id")int id){
        CloType cloType = cloTypeMapper.selectById(id);
        return cloType;
    }
}

