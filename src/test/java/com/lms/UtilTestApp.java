package com.lms;

import com.lms.redis.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = UtilApplication.class)
public class UtilTestApp {
    @Resource
    RedisCache redisCache;
    @Test
    public void test(){
       redisCache.setCacheObject("lisi","test-autoredis");
    }
}
