package com.example.project.services;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.project.exceptions.InvalidCredentialsException;
import com.example.project.models.User;
import com.example.project.models.UserClaims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.time.Duration;

@Component
public record AuthService(
        JwtService jwtService,
        UserService userService) {
    private String tokenize(User user) {
        return jwtService.tokenize("id", user.getId());
    }

    public UserClaims decode(String jwt) throws TokenExpiredException {
        DecodedJWT decodedJWT = jwtService().decode(jwt);
        Claim id = decodedJWT.getClaim("id");

        return new UserClaims(id.asString());
    }

    public Cookie login(String email, String password) throws InvalidCredentialsException {
        InvalidCredentialsException invalidCredentialsException = new InvalidCredentialsException();
        User user = userService.findByEmail(email).orElseThrow(() -> invalidCredentialsException);

        if (!user.isValidPassword(password)) {
            throw invalidCredentialsException;
        }

        String token = tokenize(user);

        return getAuthCookie(token);
    }

    private Cookie getAuthCookie(String token) {
        Cookie cookie = new Cookie("token", token);

        cookie.setMaxAge((int) Duration.ofDays(2).toMillis());
        cookie.setPath("/");
        cookie.setAttribute("SameSite", "Lax");
        cookie.setSecure(false);
        cookie.setHttpOnly(false);

        return cookie;
    }

    public Cookie logout(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "cookie");

        cookie.setAttribute("expires", "Wed, 31 Oct 2012 08:50:17 UTC");

        return cookie;
    }
}

