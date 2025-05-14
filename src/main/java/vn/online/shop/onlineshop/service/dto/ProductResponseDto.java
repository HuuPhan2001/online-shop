package vn.online.shop.onlineshop.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private String avatar;
    private String slug;
    private Double price;
    private Integer quantity;
    private Set<CategoryProductDto> categories;
    private List<VoucherResponseDto> vouchers;
}
