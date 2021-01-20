package com.wuzhi.controlled;


import com.wuzhi.entity.Record;
import com.wuzhi.entity.User;
import com.wuzhi.mapper.RecordMapper;
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
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordMapper recordMapper;

    @GetMapping("/find/findAll")
    public List findAll(){
        List<Record> records=recordMapper.selectList(null);
        return records;
    }
    @GetMapping("/find/findById/{id}")
    public Record findById(@PathVariable("id")int id){
        Record record = recordMapper.selectById(id);
        return record;
    }

}

