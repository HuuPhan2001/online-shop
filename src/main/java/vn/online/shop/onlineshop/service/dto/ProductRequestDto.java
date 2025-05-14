package vn.online.shop.onlineshop.service.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.common.config.StatusEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    private Long id;

    private StatusEnum status;

    @NotBlank(message = Constant.EMPTY_FIELD)
    @Size(min = 2, max = Constant.NAME_LENGTH, message = "Name" + Constant.NAME_LENGTH_ERROR)
    private String name;

    @Max(value = Constant.DESCRIPTION_LENGTH, message = "Description" + Constant.MAX_LENGTH_FIELD)
    private String description;

    private String slug;

    @NotNull(message = Constant.EMPTY_FIELD)
    @DecimalMin(value = "0.0", inclusive = false, message = "Price " + Constant.MIN_VALUE_VALID)
    private Double price;

    @NotNull(message = Constant.EMPTY_FIELD)
    @DecimalMin(value = "0", inclusive = false, message = "Quantity " + Constant.MIN_VALUE_VALID)
    private Integer quantity;
}
