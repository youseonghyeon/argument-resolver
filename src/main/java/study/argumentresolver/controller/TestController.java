package study.argumentresolver.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import study.argumentresolver.annotation.UserCache;
import study.argumentresolver.cache.UserCacheStore;
import study.argumentresolver.dto.User;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserCacheStore userCacheStore;


    @GetMapping("/get")
    public User get(@UserCache("MY-SESSION-ID") User user) {
        return user;
    }

    @PostMapping("/set")
    public String newCache(@RequestBody User user, HttpServletResponse response) {
        String cacheKey = UUID.randomUUID().toString();
        userCacheStore.put(cacheKey, user);
        Cookie cookie = new Cookie("MY-SESSION-ID", cacheKey);
        response.addCookie(cookie);
        return "SUCCESS";
    }
}
