package com.zhao.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-26 21:14
 */
public class ZookeeperTest {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        String node = "/zhao12";

        ZooKeeper client = new ZooKeeper("127.0.0.1:2181", 10000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("默认的watch:" + event.getType());
            }
        }, false);

        client.create(node, "zhao".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

//        Stat stat = new Stat();
        byte[] b = client.getData(node, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event.getType());
            }
        }, null);
//        System.out.println(node +"==========" + new String(b));
//        System.out.println("stat==========" + stat.toString());



        client.addWatch(node, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event);
            }
        }, AddWatchMode.PERSISTENT_RECURSIVE);



//        System.in.read();

    }
}
