package com.zhao.round_robin;

import com.zhao.ServerIps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description 平滑加权轮询[https://www.jianshu.com/p/836193df61db]
 * @date 2020-04-14 21:33
 */
public class RoundRobinWeightSmooth {

    private static Map<String, Weight> map = new HashMap<>();

    public static String getServerIp() {
        Integer total = ServerIps.WEIGHT_IPS.values().stream().reduce(Integer::sum).orElse(0);

        if (map.isEmpty()){
            ServerIps.WEIGHT_IPS.forEach((ip,weight) -> map.put(ip, new Weight(ip, weight, 0)));
        }

        map.values().forEach(w -> w.setCurrentWeight(w.getCurrentWeight() + w.getWeight()));

        Weight maxCurrentWeight = null;
        for (Weight weight : map.values()) {
            if (maxCurrentWeight == null || weight.getCurrentWeight() > maxCurrentWeight.getCurrentWeight()) {
                maxCurrentWeight = weight;
            }
        }
        maxCurrentWeight.setCurrentWeight(maxCurrentWeight.getCurrentWeight() - total);

        return maxCurrentWeight.getIp();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getServerIp());
        }
    }
}

@Setter
@Getter
@AllArgsConstructor
class Weight {
    private String ip;
    private Integer weight;
    private Integer currentWeight;
}
