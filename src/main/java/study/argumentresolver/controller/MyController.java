package study.argumentresolver.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import study.argumentresolver.annotation.UserCache;
import study.argumentresolver.cache.UserCacheStore;
import study.argumentresolver.dto.User;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyController {

    private final UserCacheStore userCacheStore;


    @GetMapping("/")
    public void test( User user) {
        log.info("cached user = {}", user);
    }

    @GetMapping("/new")
    public void newCache(HttpServletResponse res) {
        String uid = UUID.randomUUID().toString();
        userCacheStore.put(uid, new User("테스트_유저", 10));
        res.addCookie(new Cookie("my-session", uid));
    }


}
