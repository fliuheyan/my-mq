package com.mymq.nameser.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

public class KVConfigManager {
    private Logger logger = Logger.getGlobal();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private KVConfigHolder kvConfigHolder = new KVConfigHolder();

    public void load() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        URL resource = this.getClass().getClassLoader().getResource("nameser.yml");
        logger.info(" load nameser.yml from " + resource.getPath());
        try {
            kvConfigHolder.getConfigTable().putAll(mapper.readValue(resource, KVConfigHolder.class).getConfigTable());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void putKVConfig(String category, String key, String value) {
        try {
            lock.writeLock().lockInterruptibly();
            HashMap<String, String> kvConfig = kvConfigHolder.getConfigTable().get(category);
            if (kvConfig == null) {
                kvConfig = new HashMap<>();
                kvConfigHolder.getConfigTable().put(category, kvConfig);
            }
            String prev = kvConfig.put(key, value);
            if (prev == null) {
                logger.info("create new config key " + key + " value " + value);
            } else {
                logger.info("update config key " + key + "from " + prev + " to value " + value);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

