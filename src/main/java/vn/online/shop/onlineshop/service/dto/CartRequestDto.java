package vn.online.shop.onlineshop.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.online.shop.onlineshop.common.config.StatusEnum;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestDto {
    private Long id;
    private StatusEnum status;
    private Long userId;
    private List<CartItemRequestDto> itemList;
}
