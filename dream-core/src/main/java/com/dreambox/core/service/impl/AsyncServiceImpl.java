package com.dreambox.core.service.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import com.dreambox.core.service.AsyncService;

@Service("asyncService")
public class AsyncServiceImpl implements AsyncService {

    private static final int MAX_WATT = 100;
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(MAX_WATT));

    @Override
    public boolean async(Runnable command) {
        if (threadPoolExecutor.getQueue().size() >= MAX_WATT) {
            return false;
        }
        try {
            threadPoolExecutor.execute(command);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PreDestroy
    public void shutdown() {
        threadPoolExecutor.shutdown();
    }
}
