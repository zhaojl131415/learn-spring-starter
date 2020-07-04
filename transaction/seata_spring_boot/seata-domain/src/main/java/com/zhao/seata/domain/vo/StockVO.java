package com.zhao.seata.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-19 15:19
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockVO {
    private Integer commodityId;
    private Integer quantity;
}
