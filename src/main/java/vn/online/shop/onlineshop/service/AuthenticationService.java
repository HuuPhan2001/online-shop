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
import vn.online.shop.onlineshop.service.dto.AuthRequest;
import vn.online.shop.onlineshop.service.dto.AuthResponse;
import vn.online.shop.onlineshop.service.dto.RegisterRequest;
import vn.online.shop.onlineshop.service.dto.UserInfoDetails;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService implements UserDetailsService {

    private final IUserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    private final CategoryService categoryService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Constant.NOT_FOUND_USER));

        String role = Optional.ofNullable(user.getRole()).map(Category::getCode).orElse("USER");

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public AuthResponse login(AuthRequest authRequest) {
        authenticate(authRequest.getUsername(), authRequest.getPassword());

        UserDetails userDetails = loadUserByUsername(authRequest.getUsername());
        String jwt = jwtService.generateToken(userDetails);

        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(Constant.NOT_FOUND_USER));

        user.setToken(jwt);
        user = userRepository.save(user);

        return userMapper.toAuthResponse(user);
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
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại");
        }

        String rawPassword = generateRandomPassword();

        User user = userMapper.toUserRegister(registerRequest);
        user.setPassword(passwordEncoder.encode(rawPassword));

        if (user.getRoleId() == null) {
            Category defaultRole = categoryService.findCategoryDefault();
            user.setRoleId(defaultRole.getId());
        }

        user = userRepository.save(user);

//        UserDetails userDetails = loadUserByUsername(user.getUsername());
//        String jwt = jwtService.generateToken(userDetails);
//
//        user.setToken(jwt);
//        user = userRepository.save(user);

        return userMapper.toAuthResponse(user);
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
}
