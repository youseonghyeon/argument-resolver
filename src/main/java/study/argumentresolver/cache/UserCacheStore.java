package study.argumentresolver.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import study.argumentresolver.dto.User;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class UserCacheStore {

    private final Map<String, User> cache = new HashMap<>();

    public User get(String key) {
        return cache.get(key);
    }

    public void put(String key, User value) {
        cache.put(key, value);
        log.info("save success. key: {}, value: {}", key, value);
    }
}
