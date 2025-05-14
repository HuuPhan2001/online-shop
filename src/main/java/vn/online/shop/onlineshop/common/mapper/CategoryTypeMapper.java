package vn.online.shop.onlineshop.common.mapper;

import org.mapstruct.*;
import vn.online.shop.onlineshop.entity.CategoryType;
import vn.online.shop.onlineshop.service.dto.CategoryTypeCreateDto;
import vn.online.shop.onlineshop.service.dto.CategoryTypeResponse;

@Named(value = "CategoryTypeMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CategoryTypeMapper {
    @Named(value = "toEntity")
    CategoryType toEntity(CategoryTypeCreateDto dto);

    @Named(value = "toDtoCreate")
    CategoryTypeCreateDto toDtoCreate(CategoryType type);

    @Named("toResponse")
    CategoryTypeResponse toResponse(CategoryType type);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CategoryType partialUpdate(CategoryTypeCreateDto dto, @MappingTarget CategoryType type);
}
