package vn.online.shop.onlineshop.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.entity.Category;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private StatusEnum status;
    private String name;
    private String code;
    private String description;
    private Long parent;
    private String parentName;
    private String parentCode;
    private String categoryTypeName;
    private String categoryTypeCode;
}
