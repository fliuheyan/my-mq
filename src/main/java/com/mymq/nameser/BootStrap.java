package com.mymq.nameser;

import com.mymq.nameser.server.KVConfigManager;
import com.mymq.nameser.server.ShutdownHookThread;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class BootStrap {
    private Logger logger = Logger.getGlobal();
    private KVConfigManager kvConfigManager = new KVConfigManager();

    private void start() {
        kvConfigManager.load();
        // init remoting server

    }

    private void shutdown() {

    }

    public static void main(String[] args) {
        BootStrap bootStrap = new BootStrap();
        bootStrap.start();
        Runtime.getRuntime().addShutdownHook(new ShutdownHookThread((Callable<Void>) () -> {
            bootStrap.shutdown();
            return null;
        }));
    }
}
