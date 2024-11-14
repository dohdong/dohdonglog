package com.dohdonglog.controller;


import com.dohdonglog.domain.User;
import com.dohdonglog.exception.InvalidSigninInformation;
import com.dohdonglog.repository.UserRepository;
import com.dohdonglog.request.Login;
import com.dohdonglog.response.SessionResponse;
import com.dohdonglog.service.AuthService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {

        String accessToken = authService.signin(login);

        return new SessionResponse(accessToken);

    }

}
