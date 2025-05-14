package vn.online.shop.onlineshop.common.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.online.shop.onlineshop.repository.IUserRepository;
import vn.online.shop.onlineshop.service.AuthenticationService;
import vn.online.shop.onlineshop.service.CustomUserDetailService;
import vn.online.shop.onlineshop.service.dto.UserInfoDetails;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(getAuthen(request));
            }

            filterChain.doFilter(request, response);
        } catch (UsernameNotFoundException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Constant.NOT_FOUND_USER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private UsernamePasswordAuthenticationToken getAuthen(HttpServletRequest request) {
        String kongHeader = request.getHeader("kong");
        if (kongHeader != null) {
            log.info("[Kong] : `{}`", request.getRequestURI());
        }
        log.info("Check filter request");
        String jwt = getJwtFromRequest(request);
        if (!StringUtils.hasText(jwt)) {
            return null;
        }
        if (jwtService.isTokenExpired(jwt)) {
            throw new UsernameNotFoundException("Invalid jwt");
        }
        String username = jwtService.getUserIdFromJWT(jwt);
        UserInfoDetails userDetails = (UserInfoDetails) customUserDetailService.loadUserByUsername(username);
        if (userDetails == null || userDetails.getUsername().isEmpty()) {
            throw new UsernameNotFoundException("Can't find user");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken == null || bearerToken.isEmpty()) {
            String token = request.getParameter("token");
            if (token != null && !token.isEmpty()) {
                return token;
            }
            token = request.getParameter("Authorization");
            if (token != null && !token.isEmpty()) {
                return token;
            }
        }

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            log.debug("Raw Authorization header: {}", bearerToken);
            log.debug("Extracted JWT token: {}", bearerToken.substring(7).trim());

            return bearerToken.substring(7);
        }

        return null;
    }
}