package vn.online.shop.onlineshop.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.online.shop.onlineshop.entity.User;
import vn.online.shop.onlineshop.service.dto.UserInfoDetails;

@Slf4j
public class SecurityContext {
    public static User getCurrentUser() {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication != null){
                return ((UserInfoDetails) authentication.getPrincipal()).getUserInfo();
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }
}
