package com.akhilesh.LifeFund.service.Implementation;

import com.akhilesh.LifeFund.dto.request.LoginRequest;
import com.akhilesh.LifeFund.dto.request.SignupRequest;
import com.akhilesh.LifeFund.dto.response.LoginResponse;
import com.akhilesh.LifeFund.dto.response.SignupResponse;
import com.akhilesh.LifeFund.entity.User;
import com.akhilesh.LifeFund.enums.AuthProvider;
import com.akhilesh.LifeFund.exceptions.EmailAlreadyExistsException;
import com.akhilesh.LifeFund.exceptions.GoogleLoginRequiredException;
import com.akhilesh.LifeFund.exceptions.PhoneNumberAlreadyExistsException;
import com.akhilesh.LifeFund.exceptions.UserNotFoundException;
import com.akhilesh.LifeFund.repository.UserRepository;
import com.akhilesh.LifeFund.service.JWTService;
import com.akhilesh.LifeFund.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    @Override
    public SignupResponse signup(SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExistsException("Phone number already exists.");
        }

        User user = modelMapper.map(request, User.class);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setAuthProvider(AuthProvider.LOCAL);

        User savedUser = userRepository.save(user);

        return SignupResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found."));

        if (user.getAuthProvider() == AuthProvider.GOOGLE) {
            throw new GoogleLoginRequiredException(
                    "This account was created using Google. Please continue with Google Sign-In."
            );
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .build();

    }

}