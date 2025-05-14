package vn.online.shop.onlineshop.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.online.shop.onlineshop.common.config.StatusEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDto {
    private Long id;
    private StatusEnum status;
    private Long cartId;
    private ProductResponseDto product;
    private Integer quantity;
    private String note;
    private Double unitPrice;
}
