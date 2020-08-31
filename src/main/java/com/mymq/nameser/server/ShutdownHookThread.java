package com.mymq.nameser.server;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.LongAdder;
import java.util.logging.Logger;

public class ShutdownHookThread extends Thread {
    private final LongAdder longAdder = new LongAdder();
    private final Callable callback;

    public ShutdownHookThread(Callable callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        Logger logger = Logger.getGlobal();
        longAdder.increment();
        logger.info("shutting down the naming server... " + longAdder.sum() + " times");
        try {
            this.callback.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
