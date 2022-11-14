package com.zhao.seata.order.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-19 10:49
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "z_order")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商品id
     */
    private Integer commodityId;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 单价
     */
    private Integer price;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 总价
     */
    private Integer totalPrice;

    public Order(Integer userId, Integer commodityId, String commodityName, Integer price, Integer quantity, Integer totalPrice) {
        this.userId = userId;
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
