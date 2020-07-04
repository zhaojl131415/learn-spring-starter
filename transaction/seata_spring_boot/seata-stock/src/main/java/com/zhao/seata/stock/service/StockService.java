package com.zhao.seata.stock.service;

import com.zhao.seata.stock.entity.Stock;
import com.zhao.seata.stock.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-19 13:16
 */
@Slf4j
@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public void deduct(Integer commodityId, Integer quantity){
        log.info("------->Stock中扣减库存开始");
        Stock stock = stockRepository.findOneByCommodityId(commodityId);
        if (stock == null) {
            throw new NullPointerException();
        } else {
            stockRepository.deduct(commodityId, stock.getStock() - quantity);
        }
        log.info("------->Stock中扣减库存结束");
    }
}
