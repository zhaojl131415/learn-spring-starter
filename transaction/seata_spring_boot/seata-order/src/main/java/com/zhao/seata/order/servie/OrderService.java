package com.zhao.seata.order.servie;

import com.zhao.seata.domain.vo.StockVO;
import com.zhao.seata.domain.vo.UserVO;
import com.zhao.seata.order.entity.Order;
import com.zhao.seata.order.feign.StockClient;
import com.zhao.seata.order.feign.UserClient;
import com.zhao.seata.order.repository.OrderRepository;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockClient stockClient;

    @Autowired
    private UserClient userClient;

    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional(name = "zhao-seata-order",rollbackFor = Exception.class)
    public void create() {

        log.info("----->开始新建订单");
        Order order = new Order(1, 1, "iphone xs", 5300, 10, 53000);
        orderRepository.save(order);

        log.info("----->订单微服务开始调用库存，扣减库存start");
        stockClient.deduct(new StockVO(order.getCommodityId(), order.getQuantity()));
        log.info("----->订单微服务开始调用库存，扣减库存end");

        log.info("----->订单微服务开始调用用户，增加积分start");
        Integer point = Math.round(order.getTotalPrice() / 100);
        userClient.points(new UserVO(order.getUserId(), point));
        log.info("----->订单微服务开始调用用户，增加积分end");

//        int i = 100/0;

        log.info("----->下订单结束了，O(∩_∩)O哈哈~");
    }

}
