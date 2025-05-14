package vn.online.shop.onlineshop.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.common.config.StatusEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private Token tokenInfo;
    private UserResponse userResponse;
}
