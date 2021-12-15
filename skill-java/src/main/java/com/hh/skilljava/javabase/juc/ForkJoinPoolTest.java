package com.hh.skilljava.javabase.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * 分治
 *
 * @author HaoHao
 * @date 2021/12/14 8:32 下午
 */
@Slf4j
public class ForkJoinPoolTest {

    static class PrintTask extends RecursiveAction {

        private static final long serialVersionUID = -5682591773776169148L;

        // 单个任务最大执行20个打印
        private static final int MAX = 2;

        private int start;

        private int end;

        public PrintTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if ((end - start) <= MAX) {
                for (int i = start; i < end; i++) {
                    log.info("" + i);
                }
            } else {
                // 分割
                int mid = (end + start) / 2;
                PrintTask leftTask = new PrintTask(start, mid);
                PrintTask rightTask = new PrintTask(mid, end);
                leftTask.fork();
                rightTask.fork();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        forkJoinPool.submit(new PrintTask(0, 10));

        forkJoinPool.awaitTermination(5, TimeUnit.SECONDS);

        forkJoinPool.shutdown();

    }
}
