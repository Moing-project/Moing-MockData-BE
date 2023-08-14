package com.example.finalteammockdata.global.redis.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

@Service
public class AuthenticationRedisService {

    private final RedisTemplate<String, String> redisTemplateID;
    private final RedisTemplate<String, String> redisTemplateTOKEN;

    public AuthenticationRedisService(@Qualifier("authenticationIdRedis") RedisTemplate<String, String> redisTemplateID,
                                      @Qualifier("authenticationTokenRedis") RedisTemplate<String, String> redisTemplateTOKEN) {
        this.redisTemplateID = redisTemplateID;
        this.redisTemplateTOKEN = redisTemplateTOKEN;
    }


    public void setValue(String key, String value, AuthenticationTarget target) {

        if (target == AuthenticationTarget.ID) {
            redisTemplateID.opsForValue().set(key, value);
            return;
        }
        redisTemplateTOKEN.opsForValue().set(key, value);
    }

    public void setValueByExpireMinute(String key, String value, Long duration, AuthenticationTarget target) {
        Duration expireDuration = Duration.ofMinutes(duration);
        while (!SetValueAble(key, value, expireDuration, target)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setValueByExpireHour(String key, String value, Long duration, AuthenticationTarget target) {
        Duration expireDuration = Duration.ofHours(duration);
        while (!SetValueAble(key, value, expireDuration, target)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Boolean SetValueAble(final String key, final String value, final Duration duration, AuthenticationTarget target) {
        if (target == AuthenticationTarget.ID)
            return redisTemplateID.opsForValue().setIfAbsent(key, value, duration);
        return redisTemplateTOKEN.opsForValue().setIfAbsent(key, value, duration);
    }

    public void setValueStrong(final String key, final String value, final Duration duration, AuthenticationTarget target) {
        if (target == AuthenticationTarget.ID) {
            redisTemplateID.delete(key);
            redisTemplateID.opsForValue().set(key, value, duration);
            return;
        }
        redisTemplateTOKEN.delete(key);
        redisTemplateTOKEN.opsForValue().set(key, value, duration);
    }

    public String getValue(String key, AuthenticationTarget target) {
        if (target == AuthenticationTarget.ID)
            return redisTemplateID.opsForValue().get(key);
        return redisTemplateTOKEN.opsForValue().get(key);
    }

    public AuthenticationDao getValueANDExpired(String key, AuthenticationTarget target) {
        if (target == AuthenticationTarget.ID)
            return new AuthenticationDao(redisTemplateID.opsForValue().get(key), redisTemplateID.getExpire(key));
        return new AuthenticationDao(redisTemplateTOKEN.opsForValue().get(key), redisTemplateTOKEN.getExpire(key));
    }

    public Long getExpired(String key, AuthenticationTarget target) {
        if (target == AuthenticationTarget.ID)
            return redisTemplateID.getExpire(key);
        return redisTemplateTOKEN.getExpire(key);
    }

    public boolean isValue(String key, AuthenticationTarget target) {
        if (target == AuthenticationTarget.ID)
            return redisTemplateID.opsForValue().get(key) != null;
        return redisTemplateTOKEN.opsForValue().get(key) != null;
    }

    public boolean deleteKey(String key, AuthenticationTarget target){
        if (target == AuthenticationTarget.ID)
            return redisTemplateID.delete(key) != null;
        return redisTemplateTOKEN.delete(key) != null;
    }


    public enum AuthenticationTarget {
        ID, TOKEN;
    }

    public record AuthenticationDao(String key, Long expired) {
    }
}
