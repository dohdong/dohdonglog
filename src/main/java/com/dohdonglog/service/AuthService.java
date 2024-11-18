package com.dohdonglog.service;


import com.dohdonglog.domain.Session;
import com.dohdonglog.domain.User;
import com.dohdonglog.exception.InvalidSigninInformation;
import com.dohdonglog.repository.UserRepository;
import com.dohdonglog.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public String signin(Login login){
        // DB에서 조회
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(() -> new InvalidSigninInformation());

        Session session = user.addSession();

        return session.getAccessToken();
    }



}
