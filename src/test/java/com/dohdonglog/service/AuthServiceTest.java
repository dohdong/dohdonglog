package com.dohdonglog.service;

import static org.junit.jupiter.api.Assertions.*;


import com.dohdonglog.crypto.PasswordEncoder;
import com.dohdonglog.domain.User;
import com.dohdonglog.exception.AlreadyExistsEmailException;
import com.dohdonglog.exception.InvalidSigninInformation;
import com.dohdonglog.repository.UserRepository;
import com.dohdonglog.request.Login;
import com.dohdonglog.request.Signup;
import java.security.InvalidAlgorithmParameterException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;


@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1() {
        // given
        Signup signup = Signup.builder()
                .email("hodolman88@gmail.com")
                .password("1234")
                .name("호돌맨")
                .build();

        // when
        authService.signup(signup);

        // then
        assertEquals(1, userRepository.count());

        User user = userRepository.findAll().iterator().next();
        assertEquals("hodolman88@gmail.com", user.getEmail());
        assertNotNull(user.getPassword());
        assertNotEquals("1234", user.getPassword());
        assertEquals("호돌맨", user.getName());
    }

    @Test
    @DisplayName("회원가입시 중복된 이메일")
    void test2() {
        // given
        User user = User.builder()
                .email("hodolman88@gmail.com")
                .password("1234")
                .name("짱돌맨")
                .build();
        userRepository.save(user);

        Signup signup = Signup.builder()
                .email("hodolman88@gmail.com")
                .password("1234")
                .name("호돌맨")
                .build();

        // expected
        assertThrows(AlreadyExistsEmailException.class, () -> authService.signup(signup));
    }

    @Test
    @DisplayName("로그인 성공")
    void test3() {
        // given
        PasswordEncoder encoder = new PasswordEncoder();
        String encryptedPassword = encoder.encrypt("1234");
        User user = User.builder()
                .email("hodolman88@gmail.com")
                .password(encryptedPassword)
                .name("짱돌맨")
                .build();
        userRepository.save(user);

        Login login = Login.builder()
                .email("hodolman88@gmail.com")
                .password("1234").build();

        // when
        Long userId = authService.signin(login);

        // then
        assertNotNull(userId);

    }

    @Test
    @DisplayName("로그인시 비밀번호 틀림")
    void test4() {
        // given
        Signup signup = Signup.builder()
                .email("hodolman88@gmail.com")
                .password("1234")
                .name("호돌맨")
                .build();
        authService.signup(signup);

        Login login = Login.builder()
                .email("hodolman88@gmail.com")
                .password("5678").build();

        // expected
        Assertions.assertThrows(InvalidSigninInformation.class,
                ()-> authService.signin(login)) ;

        // then

    }


}
