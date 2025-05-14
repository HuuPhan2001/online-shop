package vn.online.shop.onlineshop.service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.common.config.StatusEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTypeCreateDto {
    private Long id;

    private StatusEnum status;

    @NotBlank(message = Constant.EMPTY_FIELD)
    @Size(min = 2, max = Constant.NAME_LENGTH, message = "Name" + Constant.NAME_LENGTH_ERROR)
    private String name;

    @NotBlank(message = Constant.EMPTY_FIELD)
    @Size(min = 2, max = Constant.CODE_LENGTH, message = "Code" + Constant.CODE_LENGTH_ERROR)
    private String code;

    @Max(value = Constant.DESCRIPTION_LENGTH, message = "Description" + Constant.MAX_LENGTH_FIELD)
    private String description;
}
