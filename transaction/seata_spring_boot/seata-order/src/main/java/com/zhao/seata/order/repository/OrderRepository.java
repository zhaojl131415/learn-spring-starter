package com.zhao.seata.order.repository;

import com.zhao.seata.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-19 10:47
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
