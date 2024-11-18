package com.dohdonglog.config;

import com.dohdonglog.config.data.UserSession;
import com.dohdonglog.domain.Session;
import com.dohdonglog.exception.Unauthorized;
import com.dohdonglog.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;
    private static final String KEY = "/TJQPaA3e7TuJmTQ7zekUqqr+eEqnS9f+jGjFvkA7to=";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String jws = webRequest.getHeader("Authorization");
        if (jws == null || jws.equals("")) {
            throw new Unauthorized();
        }

        byte[] decodedKey = Base64.getDecoder().decode(KEY);

        try{
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(decodedKey)
                    .build()
                    .parseClaimsJws(jws);

            String userId = claims.getBody().getSubject();
            return new UserSession(Long.parseLong(userId));

        } catch (JwtException e){
            throw new Unauthorized();
        }

//        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
//        if (servletRequest == null) {
//            log.error("servletRequest null");
//            throw new Unauthorized();
//        }
//
//        Cookie[] cookies = servletRequest.getCookies();
//        if(cookies.length == 0 ) {
//            log.error("쿠키가 없음.");
//            throw new Unauthorized();
//        }
//
//        String accessToken = cookies[0].getValue();
//
//        Session session = sessionRepository.findByAccessToken(accessToken)
//                .orElseThrow(() -> new Unauthorized());

    }
}
