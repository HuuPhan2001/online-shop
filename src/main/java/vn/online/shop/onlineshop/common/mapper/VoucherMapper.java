package vn.online.shop.onlineshop.common.mapper;

import org.mapstruct.*;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.entity.Voucher;
import vn.online.shop.onlineshop.service.dto.VoucherProductDto;
import vn.online.shop.onlineshop.service.dto.VoucherRequestDto;
import vn.online.shop.onlineshop.service.dto.VoucherResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface VoucherMapper {
    @Named(value = "toResponse")
    VoucherResponseDto toResponse(Voucher voucher);

    @Named(value = "toEntity")
    Voucher toEntity(VoucherRequestDto dto);

    @Named(value = "toProductResponse")
    VoucherProductDto toProductResponse(Voucher voucher);

    @Named("toProductResponseList")
    default List<VoucherProductDto> toProductResponseList(List<Voucher> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Voucher partialUpdate(VoucherRequestDto dto, @MappingTarget Voucher voucher);
}
