package vn.online.shop.onlineshop.common.mapper;

import org.apache.commons.lang3.ObjectUtils;
import org.mapstruct.*;
import vn.online.shop.onlineshop.entity.CartItem;
import vn.online.shop.onlineshop.entity.Category;
import vn.online.shop.onlineshop.service.dto.CartItemRequestDto;
import vn.online.shop.onlineshop.service.dto.CartItemResponseDto;
import vn.online.shop.onlineshop.service.dto.CartResponseDto;
import vn.online.shop.onlineshop.service.dto.CategoryRequestDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Named(value = "CartItemMapper")
@Mapper(uses = {ProductMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CartItemMapper {
    @Named(value = "toEntity")
    CartItem toEntity(CartItemRequestDto dto);

    @Named(value = "toResponseDto")
    @Mapping(target = "product", qualifiedByName = "toResponse")
    CartItemResponseDto toResponseDto(CartItem cartItem);

    @Named(value = "toCartItemResponseList")
    default List<CartItemResponseDto> toResponseDtoList(List<CartItem> cartItems) {
        if(ObjectUtils.isEmpty(cartItems)) {
            return null;
        }
        return cartItems.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CartItem partialUpdate(CartItemRequestDto dto, @MappingTarget CartItem cartItem);

}
