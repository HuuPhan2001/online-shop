package vn.online.shop.onlineshop.common.mapper;

import org.mapstruct.*;
import vn.online.shop.onlineshop.entity.Category;
import vn.online.shop.onlineshop.service.dto.CategoryCreateDto;
import vn.online.shop.onlineshop.service.dto.CategoryDto;

@Named(value = "CategoryMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CategoryMapper {
    @Named(value = "toEntity")
    Category toEntity(CategoryCreateDto categoryDto);

    @Named(value = "toDtoCreate")
    CategoryCreateDto toDtoCreate(Category category);

    @Named(value = "toResponse")
    @Mapping(target = "categoryTypeName", expression = "java(category.getCategoryType().getName())")
    @Mapping(target = "categoryTypeCode", expression = "java(category.getCategoryType().getCode())")
//    @Mapping(target = "parentCode", expression = "java(category.getParent().getCode())")
//    @Mapping(target = "parentName", expression = "java(category.getParent().getName())")
    CategoryDto toResponse(Category category);

    @AfterMapping
    default void linkFields(@MappingTarget CategoryDto categoryDto, Category category) {
        if(category.getParent() != null){
            categoryDto.setParentCode(category.getParentCate().getCode());
            categoryDto.setParentName(category.getParentCate().getName());
        }
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(CategoryCreateDto categoryDto, @MappingTarget Category category);
}
