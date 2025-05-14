package vn.online.shop.onlineshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.common.config.JwtService;
import vn.online.shop.onlineshop.common.mapper.UserMapper;
import vn.online.shop.onlineshop.entity.Category;
import vn.online.shop.onlineshop.entity.User;
import vn.online.shop.onlineshop.exception.RestExceptionHandler;
import vn.online.shop.onlineshop.repository.IUserRepository;
import vn.online.shop.onlineshop.service.dto.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService{

    private final IUserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    private final CategoryService categoryService;

    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(AuthRequest authRequest) {
        authenticate(authRequest.getUsername(), authRequest.getPassword());

        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(Constant.NOT_FOUND_USER));

        Token tokenInfo = jwtService.generateToken(authRequest.getUsername());
        AuthResponse authResponse = new AuthResponse(tokenInfo, userMapper.toResponse(user));
        user.setToken(tokenInfo.getAccessToken());
        userRepository.save(user);

        return authResponse;
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            log.error(e.getMessage());
            throw new RestExceptionHandler("Tên đăng nhập hoặc mật khẩu không chính xác!");
        }
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsernameOrEmail(registerRequest.getUsername(), registerRequest.getEmail())) {
            throw new RestExceptionHandler("Tên đăng nhập đã tồn tại");
        }
        String rawPassword;
        if(registerRequest.getPassword().isBlank()){
            rawPassword = registerRequest.getPassword();
        } else {
            rawPassword = Constant.DEFAULT_PASS;
        }

        User user = userMapper.toUserRegister(registerRequest);
        user.setPassword(passwordEncoder.encode(rawPassword));

        if (user.getRoleId() == null) {
            Category defaultRole = categoryService.findCategoryDefault();
            user.setRoleId(defaultRole.getId());
        }

        user = userRepository.save(user);

        user.setPassword(rawPassword);

//        UserDetails userDetails = loadUserByUsername(user.getUsername());
//        String jwt = jwtService.generateToken(userDetails);
//
//        user.setToken(jwt);
//        user = userRepository.save(user);

        return new AuthResponse(null, userMapper.toResponse(user));
    }

    private String generateRandomPassword() {
        String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerChars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialChars = "!@#$%^&*()-_=+[]{}|;:,.<>?";

        String allChars = upperChars + lowerChars + numbers + specialChars;
        Random random = new SecureRandom();

        StringBuilder password = new StringBuilder(12);

        password.append(upperChars.charAt(random.nextInt(upperChars.length())));
        password.append(lowerChars.charAt(random.nextInt(lowerChars.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));

        for (int i = 4; i < 12; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        char[] passwordArray = password.toString().toCharArray();
        for (int i = 0; i < passwordArray.length; i++) {
            int j = random.nextInt(passwordArray.length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[j];
            passwordArray[j] = temp;
        }

        return new String(passwordArray);
    }

    public boolean logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (token == null || token.isEmpty()) {
            return false;
        }

        try {
            if (!jwtService.isTokenExpired(token)) {
                return false;
            }

            String username = jwtService.extractUsername(token);

            User user = userRepository.findByUsername(username).orElse(null);

            if (user != null && token.equals(user.getToken())) {
                user.setToken(null);
                userRepository.save(user);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
