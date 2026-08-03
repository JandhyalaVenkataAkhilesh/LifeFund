package com.akhilesh.LifeFund.oauth;

import com.akhilesh.LifeFund.entity.User;
import com.akhilesh.LifeFund.enums.AuthProvider;
import com.akhilesh.LifeFund.repository.UserRepository;
import com.akhilesh.LifeFund.service.JWTService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler
        implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    private final JWTService jwtService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User =
                (OAuth2User) authentication.getPrincipal();

        String email =
                oAuth2User.getAttribute("email");

        String name =
                oAuth2User.getAttribute("name");

        User user = userRepository
                .findByEmail(email)
                .orElseGet(() -> {

                    User newUser = new User();

                    newUser.setName(name);

                    newUser.setEmail(email);

                    newUser.setPhoneNumber(
                            UUID.randomUUID()
                                    .toString()
                                    .replace("-", "")
                                    .substring(0, 10)
                    );

                    newUser.setPassword(null);

                    newUser.setAuthProvider(AuthProvider.GOOGLE);

                    return userRepository.save(newUser);

                });

        String token = jwtService.generateToken(user);

        response.sendRedirect(
                "https://lifefund.onrender.com/pages/dashboard.html?token=" + token
        );

    }

}