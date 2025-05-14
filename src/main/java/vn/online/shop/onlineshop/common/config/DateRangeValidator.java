package vn.online.shop.onlineshop.common.config;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.online.shop.onlineshop.service.dto.VoucherRequestDto;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, VoucherRequestDto> {

    @Override
    public boolean isValid(VoucherRequestDto dto, ConstraintValidatorContext context) {
        if (dto.getStartDate() == null || dto.getEndDate() == null) return true; // Để @NotNull xử lý

        return dto.getStartDate().before(dto.getEndDate());
    }
}