package com.zhao.seata.stock.repository;

import com.zhao.seata.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-19 13:14
 */
@Repository
public interface StockRepository extends JpaRepository<Stock,Integer> {


    Stock findOneByCommodityId(Integer commodityId);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update z_stock set stock = ?2 where commodity_id = ?1 ", nativeQuery = true)
    void deduct(Integer commodityId, Integer stock);

}
