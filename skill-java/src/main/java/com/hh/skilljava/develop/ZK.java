package com.hh.skilljava.develop;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author HaoHao
 * @date 2021/11/25 7:32 下午
 */

public class ZK {


    static ZooKeeper zk;



    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        zk = new ZooKeeper("127.0.0.1", 2000, new Watcher() {
            @SneakyThrows
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("event:" + JSON.toJSONString(watchedEvent));
                // 事件状态
                Event.KeeperState state = watchedEvent.getState();
                // 事件类型
                Event.EventType type = watchedEvent.getType();
                // zk路径
                String path = watchedEvent.getPath();

                if (Event.EventType.NodeDataChanged == type) {
                    System.out.println("change path:" + path);
                    System.out.println("change data:" + new String(zk.getData(path, true, null)));
                }
            }
        });

        Thread.sleep(1000);
        zk.getData("/node", true, null);
        zk.setData("/node", "444".getBytes(StandardCharsets.UTF_8), -1);
        Thread.sleep(1000);

    }
}


