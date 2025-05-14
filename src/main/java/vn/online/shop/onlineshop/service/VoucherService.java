package vn.online.shop.onlineshop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.online.shop.onlineshop.common.config.BusinessCommon;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.common.mapper.VoucherMapper;
import vn.online.shop.onlineshop.entity.Voucher;
import vn.online.shop.onlineshop.exception.RestExceptionHandler;
import vn.online.shop.onlineshop.repository.IRepository;
import vn.online.shop.onlineshop.repository.IVoucherRepository;
import vn.online.shop.onlineshop.service.dto.VoucherRequestDto;
import vn.online.shop.onlineshop.service.dto.VoucherResponseDto;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoucherService extends BaseService<Voucher> {
    private final IVoucherRepository voucherRepository;
    private final VoucherMapper voucherMapper;

    @Override
    public IRepository<Voucher> getRepository() {
        return voucherRepository;
    }

    public VoucherResponseDto createVoucher(VoucherRequestDto voucherRequestDto) {
        if (voucherRepository.existsByCodeAndStatusAndEndDateAfter(voucherRequestDto.getCode(), StatusEnum.ACTIVE, new Date())) {
            throw new RestExceptionHandler(Constant.EXIST_VOUCHER_CODE);
        }
        Voucher voucher = voucherMapper.toEntity(voucherRequestDto);
        voucher = voucherRepository.save(voucher);
        return voucherMapper.toResponse(voucher);
    }

    public VoucherResponseDto updateVoucher(Long id, VoucherRequestDto voucherRequestDto) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RestExceptionHandler(Constant.NOT_FOUND_VOUCHER));
        voucherMapper.partialUpdate(voucherRequestDto, voucher);
        voucher = voucherRepository.save(voucher);
        return voucherMapper.toResponse(voucher);
    }

    public VoucherResponseDto updateVoucherStatus(Long id, StatusEnum status) {
        Voucher voucher = voucherRepository.findByIdAndNotDeleted(id);
        if (Objects.isNull(voucher) || ObjectUtils.isEmpty(voucher)) {
            throw new RestExceptionHandler(Constant.NOT_FOUND_VOUCHER);
        }
        voucher = BusinessCommon.validateUpdateStatus(status, voucher);
        return voucherMapper.toResponse(voucherRepository.save(voucher));
    }

    public VoucherResponseDto getVoucher(Long id) {
        return voucherRepository.findById(id).map(voucherMapper::toResponse).orElseThrow(() -> new RestExceptionHandler(Constant.NOT_FOUND_VOUCHER));
    }

    public Page<VoucherResponseDto> findAllVoucher(String text, Date startDate, Date endDate, StatusEnum status, Pageable pageable) {
        Page<Voucher> vouchers = voucherRepository.findAllVoucher(text, startDate, endDate, status, pageable);
//        List<VoucherResponseDto> list = vouchers.getContent().stream().map(voucherMapper::toResponse).toList();
//        return new PageImpl<>(list, vouchers.getPageable(), vouchers.getTotalElements());
        return BusinessCommon.mapPage(vouchers, voucherMapper::toResponse);
    }

}
