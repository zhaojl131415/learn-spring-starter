package com.zhao.seata.stock.controller;

import com.zhao.seata.domain.vo.StockVO;
import com.zhao.seata.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-19 13:10
 */
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping(value = "/deduct")
    public void deduct(@RequestBody @Valid StockVO vo) {
        stockService.deduct(vo.getCommodityId(), vo.getQuantity());
    }
}
