package com.wuzhi.controlled;


import com.wuzhi.entity.Log;
import com.wuzhi.entity.User;
import com.wuzhi.mapper.LogMapper;
import org.apache.log4j.Logger;
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
@RequestMapping("/log")
public class LogController {
    Logger logger= Logger.getLogger(Logger.class);
    private LogMapper logMapper;

    @GetMapping("/test")
    public void logtest(){
        logger.info("hello logger,此内容为log4j测试内容，可删除");
        logger.debug("debug 级别内容");
        logger.error("error");
        logger.warn("warn");
    }
    @GetMapping("/findAll")
    public List findAll(){
        List<Log> logs=logMapper.selectList(null);
        return logs;
    }
    @GetMapping("/findById")
    public Log findById(){
        Log logs=logMapper.selectById(1L);
        return logs;
    }

}

