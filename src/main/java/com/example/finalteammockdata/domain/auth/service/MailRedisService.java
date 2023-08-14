package com.example.finalteammockdata.domain.auth.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class MailRedisService {
    private final ValueOperations<Integer, String> valueOperations;

    public MailRedisService(@Qualifier("mailRedisTemplate") RedisTemplate<Integer, String> redisTemplate) {
        this.valueOperations = redisTemplate.opsForValue();
    }
    public String getValue(Integer key) {
        return valueOperations.get(key);
    }
    public String getValueAndDelete(Integer key) {
        String value = valueOperations.get(key);
        valueOperations.getOperations().delete(key);
        return value;
    }
    public void setValue(Integer key, String value) {
        valueOperations.set(key, value);
    }

    public void setValueByExpireMinute(Integer key, String value, Long duration) {
        Duration expireDuration = Duration.ofMinutes(duration);
        while (!SetValueAble(key, value, expireDuration)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Boolean SetValueAble(final Integer key, final String value, final Duration duration) {
        return valueOperations.setIfAbsent(key, value, duration);
    }
}
