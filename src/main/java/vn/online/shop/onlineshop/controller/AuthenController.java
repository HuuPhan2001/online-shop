package vn.online.shop.onlineshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.online.shop.onlineshop.common.config.JwtService;
import vn.online.shop.onlineshop.service.AuthenticationService;
import vn.online.shop.onlineshop.service.dto.AuthRequest;
import vn.online.shop.onlineshop.service.dto.AuthResponse;
import vn.online.shop.onlineshop.service.dto.RegisterRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenController {
    private final AuthenticationService authenticationService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(authenticationService.login(authRequest), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        return new ResponseEntity<>(authenticationService.register(registerRequest), HttpStatus.OK);
    }
}
