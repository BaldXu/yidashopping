package com.wuzhi.service.impl;

import com.wuzhi.entity.Stock;
import com.wuzhi.mapper.StockMapper;
import com.wuzhi.service.IStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2020-11-18
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements IStockService {

}
