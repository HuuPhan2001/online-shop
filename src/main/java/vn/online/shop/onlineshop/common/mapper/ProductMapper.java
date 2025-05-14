package vn.online.shop.onlineshop.common.mapper;

import org.mapstruct.*;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.entity.CategoryType;
import vn.online.shop.onlineshop.entity.Product;
import vn.online.shop.onlineshop.service.dto.CategoryTypeCreateDto;
import vn.online.shop.onlineshop.service.dto.ProductRequestDto;
import vn.online.shop.onlineshop.service.dto.ProductResponseDto;

import static vn.online.shop.onlineshop.common.config.Constant.TO_DTO_WITHOUT_RELATIONSHIPS;

@Named(value = "ProductMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CategoryMapper.class, VoucherMapper.class}, componentModel = "spring")
public interface ProductMapper {
    @Named(value = "toResponse")
    @Mappings({
            @Mapping(target = "categories", qualifiedByName = "toProductResponseSet"),
            @Mapping(target = "vouchers", qualifiedByName = "toProductResponseList")
    })
    ProductResponseDto toResponse(Product product);

    @Named(value = "toEntity")
    Product toEntity(ProductRequestDto productRequestDto);

    @Named(TO_DTO_WITHOUT_RELATIONSHIPS)
    @Mappings({
            @Mapping(target = "categories", expression = "java(null)"),
            @Mapping(target = "vouchers", expression = "java(null)")
    })
    ProductResponseDto toResponseWithoutRelate(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductRequestDto dto, @MappingTarget Product product);
}
