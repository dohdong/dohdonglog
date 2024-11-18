package com.dohdonglog.controller;


import com.dohdonglog.domain.User;
import com.dohdonglog.exception.InvalidSigninInformation;
import com.dohdonglog.repository.UserRepository;
import com.dohdonglog.request.Login;
import com.dohdonglog.response.SessionResponse;
import com.dohdonglog.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Optional;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    private static final String KEY = "/TJQPaA3e7TuJmTQ7zekUqqr+eEqnS9f+jGjFvkA7to=";

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {

        Long userId = authService.signin(login);

//        return new SessionResponse(accessToken);

//        ResponseCookie cookie = ResponseCookie.from("SESSION", accessToken)
//                .domain("localhost") // todo 서버 환경에 따른 분리 필요
//                .path("/")
//                .httpOnly(true)
//                .secure(false)
//                .maxAge(Duration.ofDays(30))
//                .sameSite("Strict")
//                .build();
//
//        log.info(">>>> cookie={}", cookie);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.SET_COOKIE, cookie.toString())
//                .build();

//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        byte[] encodedKey = key.getEncoded();
//        String strkey = Base64.getEncoder().encodeToString(encodedKey);

        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(KEY));

        String jws = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .signWith(key)
                .compact();

        return new SessionResponse(jws);


    }

}
