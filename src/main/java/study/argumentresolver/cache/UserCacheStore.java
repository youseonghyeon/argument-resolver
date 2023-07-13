package study.argumentresolver.cache;

import org.springframework.stereotype.Component;
import study.argumentresolver.dto.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserCacheStore {

    private Map<String, User> userMap = new HashMap<>();

    public User get(String key) {
        return userMap.get(key);
    }

    public void put(String key, User value) {
        userMap.put(key, value);
    }
}
