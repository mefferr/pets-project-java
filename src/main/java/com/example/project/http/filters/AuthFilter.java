package com.example.project.http.filters;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.project.models.User;
import com.example.project.models.UserClaims;
import com.example.project.services.AuthService;
import com.example.project.services.JwtService;
import com.example.project.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import java.io.IOException;
import java.util.Optional;

@Component
public class AuthFilter extends BasicAuthenticationFilter {

    private final AuthService authService;
    private final UserService userService;

    public AuthFilter(AuthenticationManager authenticationManager, AuthService authService, UserService userService) {
        super(authenticationManager);
        this.authService = authService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        Cookie cookie = WebUtils.getCookie(request, "token");

        if (cookie == null) {
            chain.doFilter(request, response);
            return;
        }

        UserClaims jwt = null;
        try {
            jwt = authService.decode(cookie.getValue());
        } catch (TokenExpiredException e) {
            throw new ServletException(e);
        }
        Optional<User> optionalUser = userService.findById(jwt.id());

        if (optionalUser.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        User user = optionalUser.get();

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getId(), user.getId(), user.getAuthorities());

        auth.setDetails(user);

        SecurityContextHolder.getContext().setAuthentication(auth);
        chain.doFilter(request, response);
    }
}
