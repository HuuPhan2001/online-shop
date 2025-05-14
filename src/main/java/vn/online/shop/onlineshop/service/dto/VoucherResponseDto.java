package vn.online.shop.onlineshop.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.online.shop.onlineshop.common.config.StatusEnum;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherResponseDto {
    private Long id;
    private StatusEnum status;
    private String code;
    private String description;
    private Integer discountAmount;
    private Integer minOrderAmount;
    private Integer quantity;
    private Date startDate;
    private Date endDate;
}
