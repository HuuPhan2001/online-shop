package vn.online.shop.onlineshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.entity.Category;
import vn.online.shop.onlineshop.entity.User;
import vn.online.shop.onlineshop.repository.IUserRepository;
import vn.online.shop.onlineshop.service.dto.UserInfoDetails;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Constant.NOT_FOUND_USER));

        String role = Optional.ofNullable(user.getRole())
                .map(Category::getCode)
                .orElse("USER");

        return new UserInfoDetails(Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+role)), user);
    }
}
