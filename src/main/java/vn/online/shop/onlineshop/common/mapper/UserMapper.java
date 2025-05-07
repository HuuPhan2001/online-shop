package vn.online.shop.onlineshop.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import vn.online.shop.onlineshop.entity.User;
import vn.online.shop.onlineshop.service.dto.AuthResponse;
import vn.online.shop.onlineshop.service.dto.RegisterRequest;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    @Named("toAuthResponse")
    @Mapping(target = "roleName", expression = "java(user.getRole().getName())")
    AuthResponse toAuthResponse(User user);

    @Named("toUserRegister")
    User toUserRegister(RegisterRequest registerRequest);
}
