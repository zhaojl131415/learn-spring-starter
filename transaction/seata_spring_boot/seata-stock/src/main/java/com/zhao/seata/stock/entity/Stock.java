package com.zhao.seata.stock.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-19 12:54
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="z_stock")
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
    * id
    */
    private Integer id;

    /**
    * commodity_id
    */
    private Integer commodityId;

    /**
    * stock
    */
    private Integer stock;


}
