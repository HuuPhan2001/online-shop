package vn.online.shop.onlineshop.service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.common.config.ValidDateRange;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidDateRange
public class VoucherRequestDto {
    private Long id;
    private StatusEnum status;

    @NotBlank(message = Constant.EMPTY_FIELD)
    @Size(min = 2, max = Constant.CODE_LENGTH, message = "Code" + Constant.CODE_LENGTH_ERROR)
    private String code;

    @Max(value = Constant.DESCRIPTION_LENGTH, message = "Description" + Constant.MAX_LENGTH_FIELD)
    private String description;

    @NotNull(message = Constant.EMPTY_FIELD)
    @DecimalMin(value = "0", inclusive = false, message = "Discount " + Constant.MIN_VALUE_VALID)
    private Integer discountAmount;

    @NotNull(message = Constant.EMPTY_FIELD)
    @DecimalMin(value = "0", inclusive = false, message = "Min Order " + Constant.MIN_VALUE_VALID)
    private Integer minOrderAmount;

    @NotNull(message = Constant.EMPTY_FIELD)
    @DecimalMin(value = "0", inclusive = false, message = "Quantity " + Constant.MIN_VALUE_VALID)
    private Integer quantity;

    private Date startDate;

    @Future(message = "End date must be in the future")
    private Date endDate;

    private Long productId;
}
