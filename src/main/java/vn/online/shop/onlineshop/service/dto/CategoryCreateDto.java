package vn.online.shop.onlineshop.service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.bcel.Const;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.common.config.StatusEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateDto {
    private Long id;

    private StatusEnum status;

    @NotBlank(message = Constant.EMPTY_FIELD)
    @Size(min = 2, max = Constant.NAME_LENGTH, message = "Name" + Constant.NAME_LENGTH_ERROR)
    private String name;

    @Max(value = Constant.DESCRIPTION_LENGTH, message = "Description" + Constant.MAX_LENGTH_FIELD)
    private String description;

    private Long parent;

    @NotBlank(message = Constant.EMPTY_FIELD)
    @Size(min = 2, max = Constant.CODE_LENGTH, message = "Code" + Constant.CODE_LENGTH_ERROR)
    private String code;

//    @NotBlank(message = Constant.EMPTY_FIELD)
//    @Size(min = 2, max = Constant.CODE_LENGTH, message = "Code" + Constant.CODE_LENGTH_ERROR)
//    private String categoryTypeCode;
    @NotBlank(message = Constant.EMPTY_FIELD)
    private Long categoryTypeId;

    private boolean isDefault;

}
