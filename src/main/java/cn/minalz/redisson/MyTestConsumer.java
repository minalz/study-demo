package cn.minalz.redisson;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.redisson.api.stream.StreamReadGroupArgs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 测试创建-消费者
 */
@Slf4j
public class MyTestConsumer {

    private final RStream<String, String> myTestStream;
    private final String consumerName;
    private final RedissonClient redissonClient;
    private final ExecutorService executorService;

    public MyTestConsumer(RedissonClient redisson, String consumerName) {
        this.redissonClient = redisson;
        this.myTestStream = redisson.getStream("myTestStream");
        this.consumerName = consumerName;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * 启动处理测试的线程
     * 该方法创建并启动一个新的线程，用于不断尝试读取和处理测试消息
     * 线程会一直运行，直到被显式中断
     */
    public void startProcessing() {
        executorService.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Map<StreamMessageId, Map<String, String>> messages =
                            myTestStream.readGroup("my_test_group", consumerName,
                                    StreamReadGroupArgs.neverDelivered().count(100));
                    if (messages.isEmpty()) {
                        cleanUpProcessedMessages();
                        Thread.sleep(5000);
                        continue;
                    }
 
                    messages.forEach((id, messageData) -> {
                        try {
                            String testDataId = messageData.get("testDataId");
                            if (StrUtil.isBlank(testDataId)) {
                                return;
                            }
                            // 获取分布式锁，锁的名称可以根据 testDataId 来生成
                            RLock lock = redissonClient.getLock("myTestLock:" + id);
                            // 尝试获取锁，最多等待3秒，锁的持有时间为10秒
                            boolean isLocked = lock.tryLock(3, 10, TimeUnit.SECONDS);
                            if (isLocked) {
                                try {
                                    log.info("测试消息消费成功, id: {}, testDataId: {}", id, testDataId);
                                }finally {
                                    // 释放锁
                                    lock.unlock();
                                }
                            } else {
                                // 锁获取失败，可能是其他线程正在处理同一个 testDataId
                                log.warn("测试消息消费跳过, id: {}, testDataId: {}, 原因: 锁获取失败", id, testDataId);
                            }
                        } catch (InterruptedException e) {
                            // 如果线程被中断，设置当前线程的中断状态
                            Thread.currentThread().interrupt();
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error("测试消费者处理数据发生了异常: {}", e.getMessage());
                        }
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("测试消费异常: {}", e.getMessage());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
    }
 
    private void cleanUpProcessedMessages() {
        try {
            PendingResult pending = myTestStream.getPendingInfo("my_test_group");
            if (pending.getTotal() == 0) {
                return;
            }
 
            Thread.sleep(2000);
 
            Map<StreamMessageId, Map<String, String>> processingGroup = myTestStream.readGroup("my_test_group", consumerName,
                    StreamReadGroupArgs.greaterThan(pending.getLowestId()).count((int) pending.getTotal() - 1));
            List<StreamMessageId> ids = new ArrayList<>(processingGroup.keySet());
            ids.add(pending.getLowestId());
 
            myTestStream.ack("my_test_group", ids.toArray(new StreamMessageId[0]));
            log.info("确认【{}】条测试消息已处理", ids.size());
 
            long remove = myTestStream.remove(ids.toArray(new StreamMessageId[0]));
            log.info("清理处理完成的测试消息成功: {}", remove);
        } catch (InterruptedException e) {
            log.error("清理测试消息过程中线程被中断");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("清理测试消息过程中发生异常: {}", e.getMessage());
        }
    }

}