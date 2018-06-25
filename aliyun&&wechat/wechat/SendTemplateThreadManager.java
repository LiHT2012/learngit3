package com.backend.kfc.wechat;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.backend.kfc.wechat.model.MiniProgramTemplateMessage;

@Component
public class SendTemplateThreadManager {

    private static int CORE_POOL_SIZE = 5;
    private static int MAX_POOL_SIZE = 10;
    private static long KEEPALIVE_TIME = 60L;//seconds
    private static int WORK_QUEUE_LENGTH = 10000;
    private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(WORK_QUEUE_LENGTH);
    
    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 
            KEEPALIVE_TIME, TimeUnit.SECONDS, workQueue);
    @PreDestroy
    private void destory() {
        threadPool.shutdown();
    }
    
    public void sendMessage(MiniProgramTemplateMessage message, String url) {
        threadPool.execute(new SendTemplateMessageTask(message, url));
    }
    
}
