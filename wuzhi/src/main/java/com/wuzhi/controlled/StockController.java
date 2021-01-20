package com.wuzhi.controlled;


import com.wuzhi.entity.Stock;
import com.wuzhi.entity.User;
import com.wuzhi.mapper.ClothingMapper;
import com.wuzhi.mapper.StockMapper;
import com.wuzhi.util.updateStock;
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
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private ClothingMapper clothingMapper;

    @GetMapping("/find/findAll")
    public List findAll(){
        List<Stock> stocks=stockMapper.selectList(null);
        return stocks;
    }
    @GetMapping("/find/findById/{id}")
    public Stock findById(@PathVariable("id")int id){
        Stock stock = stockMapper.selectById(id);
        return stock;
    }
    @GetMapping("/updateStock")
    public void updateStock(){
        updateStock updateStock = new updateStock();
        updateStock.addStock(stockMapper,clothingMapper);
        updateStock.update(stockMapper,clothingMapper);
    }

}

