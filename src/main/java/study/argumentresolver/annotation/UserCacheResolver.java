package study.argumentresolver.annotation;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import study.argumentresolver.cache.UserCacheStore;
import study.argumentresolver.dto.User;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCacheResolver implements HandlerMethodArgumentResolver {

    private final UserCacheStore userCacheStore;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // UserCache annotation이 존재하고, User 타입인 경우 true 반환
        return parameter.hasParameterAnnotation(UserCache.class)
                && parameter.getGenericParameterType().equals(User.class);
    }

    /**
     * UserCache annotation이 존재하는 경우, UserCacheStore에서 cacheKey에 해당하는 User 객체를 반환한다.
     * @return User (nullable)
     */
    @Override
    public User resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest req = webRequest.getNativeRequest(HttpServletRequest.class);
        String sessionName = parameter.getParameterAnnotation(UserCache.class).value();

        Optional<Cookie> cacheCookie = findCookieByName(req.getCookies(), sessionName);
        String cacheKey = cacheCookie.map(Cookie::getValue).orElse("");

        return userCacheStore.get(cacheKey);
    }

    private Optional<Cookie> findCookieByName(@Nullable Cookie[] cookies, String sessionName) {
        if (cookies == null) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(sessionName))
                .findFirst();

    }
}
