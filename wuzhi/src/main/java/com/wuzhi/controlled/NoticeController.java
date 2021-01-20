package com.wuzhi.controlled;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhi.entity.Notice;
import com.wuzhi.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeMapper noticeMapper;

    @GetMapping("/findAll")
    public List findAll(){
        List<Notice> notices=noticeMapper.selectList(null);
        return notices;
    }
    @GetMapping("/findById/{id}")
    public Notice findById(@PathVariable("id") int id){
        Notice notice = noticeMapper.selectById(id);
        return notice;
    }
    @GetMapping("/getNewNotice")
    public Notice getNewNotice(){
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper
                .orderByDesc("time")
                .last("limit "+1);
        List<Notice> notices= noticeMapper.selectList(wrapper);
        return notices.get(0);
    }
    @GetMapping("/findByTitle/{title}")
    public List findByName(@PathVariable("title") String title){
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper
                .like("title",title);
        List<Notice> notices= noticeMapper.selectList(wrapper);
        return notices;
    }
    //插入、保存
    @PostMapping("/save")
    public String save(@RequestBody Notice notice){
        int i=noticeMapper.insert(notice);
        if (i==0){
            return "失败";
        }else {
            return "成功";
        }
    }
    //修改
    @PostMapping("/update")
    public String update(@RequestBody Notice notice){
        int i=noticeMapper.updateById(notice);
        if (i==0){
            return "失败";
        }else {
            return "成功";
        }
    }
}

