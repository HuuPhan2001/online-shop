package vn.online.shop.onlineshop.common.mapper;

import org.mapstruct.*;
import vn.online.shop.onlineshop.entity.Category;
import vn.online.shop.onlineshop.entity.CategoryType;
import vn.online.shop.onlineshop.entity.User;
import vn.online.shop.onlineshop.service.dto.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CategoryMapper.class},componentModel = "spring")
public interface UserMapper {
    @Named(value = "toAuthResponse")
    AuthResponse toAuthResponse(User user);

    @Named(value = "toUserRegister")
    User toUserRegister(RegisterRequest registerRequest);

    @Named(value = "toResponse")
    UserResponse toResponse(User user);

    @Named(value = "toDto")
    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(RegisterRequest dto, @MappingTarget User user);
}
