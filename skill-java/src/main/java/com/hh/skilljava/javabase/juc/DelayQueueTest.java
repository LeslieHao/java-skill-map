package com.hh.skilljava.javabase.juc;

import lombok.SneakyThrows;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延时队列
 *
 * 缓存系统的设计：
 * 用 DelayQueue 保存缓存元素的有效期，使用一个线程循环查询 DelayQueue，
 * 如果能从 DelayQueue 中获取元素，说明缓存有效期到了
 *
 * 定时任务调度：
 * 用 DelayQueue 保存当天会执行的任务以及时间，
 * 如果能从 DelayQueue 中获取元素，任务就可以开始执行了。比如 TimerQueue 就是这样实现的
 *
 * @author HaoHao
 * @date 2021/12/22 7:48 下午
 */
public class DelayQueueTest {

    static class DelayTask implements Delayed {

        // 触发时间点
        private final long time;

        private String key;

        public DelayTask(long delay, String key) {
            this.key = key;
            this.time = System.currentTimeMillis() + delay;
        }

        public String getKey() {
            return key;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(time - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }
    }


    /**
     * 过期自动删除map
     * @param <K>
     * @param <V>
     */
    static class CacheMap<K, V> extends ConcurrentHashMap<K, V> {

        private static final long serialVersionUID = -6354298003205516741L;

        DelayQueue<DelayTask> delayQueue = new DelayQueue<>();

        public CacheMap() {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    while (true) {
                        DelayTask take = delayQueue.take();
                        remove(take.getKey());
                    }
                }
            }).start();
        }

        public V put(K key, V value, long expire) {
            V put = super.put(key, value);
            delayQueue.add(new DelayTask(expire, (String) key));
            return put;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CacheMap<String, String> cacheMap = new CacheMap<>();
        cacheMap.put("1", "2", 2000);
        System.out.println(cacheMap.get("1"));
        Thread.sleep(3000);
        System.out.println(cacheMap.get("1"));
    }
}
