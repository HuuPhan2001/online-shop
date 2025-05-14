package vn.online.shop.onlineshop.common.mapper;

import org.apache.commons.lang3.ObjectUtils;
import org.mapstruct.*;
import vn.online.shop.onlineshop.entity.Category;
import vn.online.shop.onlineshop.service.dto.CategoryProductDto;
import vn.online.shop.onlineshop.service.dto.CategoryRequestDto;
import vn.online.shop.onlineshop.service.dto.CategoryDto;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Named(value = "CategoryMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CategoryMapper {
    @Named(value = "toEntity")
    Category toEntity(CategoryRequestDto categoryDto);

    @Named(value = "toDtoCreate")
    CategoryRequestDto toDtoCreate(Category category);

    @Named(value = "toResponse")
    @Mapping(target = "categoryTypeName", ignore = true)
    @Mapping(target = "categoryTypeCode", ignore = true)
    @Mapping(target = "parentCode", ignore = true)
    @Mapping(target = "parentName", ignore = true)
    CategoryDto toResponse(Category category);

    @AfterMapping
    default void linkFields(@MappingTarget CategoryDto dto, Category category) {
        if (category.getCategoryType() != null) {
            dto.setCategoryTypeName(category.getCategoryType().getName());
            dto.setCategoryTypeCode(category.getCategoryType().getCode());
        }

        if (category.getParentCate() != null) {
            dto.setParentCode(category.getParentCate().getCode());
            dto.setParentName(category.getParentCate().getName());
        }
    }

    @Named(value = "toProductResponse")
    CategoryProductDto toProductResponse(Category category);

    @Named("toProductResponseSet")
    default Set<CategoryProductDto> toProductResponseSet(Set<Category> categories) {
        if (ObjectUtils.isEmpty(categories)) {
            return null;
        }
        return categories.stream()
                .map(this::toProductResponse)
                .collect(Collectors.toSet());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(CategoryRequestDto categoryDto, @MappingTarget Category category);
}
