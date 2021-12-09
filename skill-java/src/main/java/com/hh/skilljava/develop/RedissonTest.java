package com.hh.skilljava.develop;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author HaoHao
 * @date 2021/12/9 7:51 下午
 */
public class RedissonTest {


    public static void main(String[] args) {
        Config config = new Config();

        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setPassword("dong!1990");

        RedissonClient redissonClient = Redisson.create(config);

        RLock lock = redissonClient.getLock("lock");
        // 底层会有个hash轮 不断续期.
        lock.lock();

    }
}
