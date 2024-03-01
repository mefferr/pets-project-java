package com.example.project.api;

import com.example.project.exceptions.InvalidCredentialsException;
import com.example.project.models.Message;
import com.example.project.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final AuthService authService;

    public record LoginData(String email, String password) {
    }

    @PostMapping("/login")
    public Message login(@RequestBody LoginData loginData, HttpServletResponse response) throws InvalidCredentialsException {
        Cookie login = authService.login(loginData.email, loginData.password);

        response.addCookie(login);

        return new Message("Success");
    }
}
