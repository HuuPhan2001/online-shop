package vn.online.shop.onlineshop.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.online.shop.onlineshop.common.config.Constant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    private String password;

    @NotBlank(message = Constant.EMPTY_FIELD)
    @Size(min = 2, max = Constant.NAME_LENGTH, message = "Full Name" + Constant.NAME_LENGTH_ERROR)
    private String fullName;

    @NotBlank(message = Constant.EMPTY_FIELD)
    @Pattern(regexp = "^0\\d{9}$", message = "Phone " + Constant.FIELD_INVALID)
    private String phone;

    @NotBlank(message = Constant.EMPTY_FIELD)
    @Pattern(regexp = "\\d{12}", message = "Identification Number " + Constant.FIELD_INVALID)
    private String identify;

    private String address;
}
