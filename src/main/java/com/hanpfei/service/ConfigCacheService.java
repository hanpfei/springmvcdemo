package com.hanpfei.service;

import com.alibaba.fastjson.JSON;
import com.hanpfei.meta.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.List;

@Service
public class ConfigCacheService {
    @Autowired
    private ShardedJedisPool shardedJedisPool;

    public void put(String key, String value, int time) {
        ShardedJedis jedis = shardedJedisPool.getResource();
        try {
            jedis.setex(key, time, value);
        } finally {
            shardedJedisPool.returnResourceObject(jedis);
        }
    }

    public String get(String key) {
        ShardedJedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.get(key);
        } finally {
            shardedJedisPool.returnResourceObject(jedis);
        }
    }


    public List<Config> getConfigItemByProductKey(String productKey) {
        ShardedJedis jedis = shardedJedisPool.getResource();
        List<Config> configs = null;
        try {
            String result = jedis.get(productKey);
            configs = JSON.parseArray(result, Config.class);
        } finally {
            shardedJedisPool.returnResourceObject(jedis);
        }

        return configs;
    }
}
