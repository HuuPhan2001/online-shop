package vn.online.shop.onlineshop.common.mapper;

import org.mapstruct.*;
import vn.online.shop.onlineshop.entity.Cart;
import vn.online.shop.onlineshop.entity.User;
import vn.online.shop.onlineshop.service.dto.CartRequestDto;
import vn.online.shop.onlineshop.service.dto.CartResponseDto;
import vn.online.shop.onlineshop.service.dto.RegisterRequest;

@Named(value = "CartMapper")
@Mapper(uses = {UserMapper.class, CartItemMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CartMapper {
    @Named(value = "toEntity")
    Cart toEntity(CartRequestDto requestDto);

    @Named(value = "toResponseDto")
    @Mappings({
            @Mapping(target = "itemList", qualifiedByName = "toCartItemResponseList"),
            @Mapping(target = "user", qualifiedByName = "toResponse")
    })
    CartResponseDto toResponseDto(Cart cart);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Cart partialUpdate(CartRequestDto dto, @MappingTarget Cart cart);
}
