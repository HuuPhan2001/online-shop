package vn.online.shop.onlineshop.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.common.config.StatusEnum;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    @JsonIgnore
    private Long id;
    private StatusEnum status;

    @NotBlank(message = Constant.EMPTY_FIELD)
    private String username;

    private String email;
    @JsonIgnore
    private String password;
    private String fullName;
    private String phone;
    private String identify;
    private String address;
    private String salt;
    private String token;
    private Long roleId;
    private boolean forgotPassword;
    private boolean rememberPassword;
}