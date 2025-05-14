package vn.online.shop.onlineshop.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.online.shop.onlineshop.common.config.StatusEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequestDto {
    private Long id;
    private StatusEnum status;
    private Long cartId;
    private Long productId;
    private Integer quantity;
    private String note;
    private Double unitPrice;
}
