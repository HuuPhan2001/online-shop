package vn.online.shop.onlineshop.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import vn.online.shop.onlineshop.common.config.StatusEnum;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserResponse implements Serializable {
    private Long id;
    private StatusEnum status;
    private Date createAt;
    private Date updateAt;
    private Long createBy;
    private Long updateBy;
    private String fullName;
    private String username;
    private boolean gender;
    private String identify;
    private Boolean rememberPassword;
    private Boolean forgotPassword;
    private CategoryDto role;
}
