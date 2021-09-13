package com.hh.skillcs.principles;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author HaoHao
 * @date 2021/5/26 3:49 下午
 */
@Slf4j
public class DisruptorTest {

    /**
     * 生产者生产的对象
     */
    @Data
    public static class LongEvent {
        private long id;
    }

    // 指定RingBuffer大小
    // 必须是2的N次方
    static int bufferSize = 1024;

    public static void main(String[] args) throws InterruptedException {
        //构建Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>
                (new EventFactory<LongEvent>() {
                    @Override
                    public LongEvent newInstance() {
                        return new LongEvent();
                    }
                }, bufferSize, DaemonThreadFactory.INSTANCE);

        //注册事件处理器
        disruptor.handleEventsWith((event, sequence, endOfBatch) -> System.out.println("E: " + event));

        //启动Disruptor
        disruptor.start();

        //获取RingBuffer
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        for (long l = 0; l < 10; l++) {
            //生产者生产消息
            ringBuffer.publishEvent(LongEvent::setId);
            Thread.sleep(1000);
        }

    }

}
