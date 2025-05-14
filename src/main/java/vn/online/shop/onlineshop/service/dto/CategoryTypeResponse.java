package vn.online.shop.onlineshop.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.online.shop.onlineshop.common.config.StatusEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryTypeResponse {
    private Long id;
    private StatusEnum status;
    private String name;
    private String code;
    private String description;
    private String createBy;
}
