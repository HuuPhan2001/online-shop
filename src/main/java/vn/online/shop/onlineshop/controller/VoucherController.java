package vn.online.shop.onlineshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.online.shop.onlineshop.common.config.BusinessCommon;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.service.VoucherService;
import vn.online.shop.onlineshop.service.dto.ProductRequestDto;
import vn.online.shop.onlineshop.service.dto.ProductResponseDto;
import vn.online.shop.onlineshop.service.dto.VoucherRequestDto;
import vn.online.shop.onlineshop.service.dto.VoucherResponseDto;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/voucher")
public class VoucherController {
    enum SortBy {
        UPDATE_AT("updateAt"), CODE("code"), ID("id"), MIN_ORDER("minOrderAmount");

        private String field;

        private SortBy(String field) {
            this.field = field;
        }
    }

    private final VoucherService voucherService;

    @PostMapping("/create")
    public ResponseEntity<VoucherResponseDto> createVoucher(@RequestBody @Valid VoucherRequestDto voucherRequestDto) {
        return new ResponseEntity<>(voucherService.createVoucher(voucherRequestDto), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VoucherResponseDto> update(@PathVariable Long id , @RequestBody @Valid VoucherRequestDto input) {
        return new ResponseEntity<>(voucherService.updateVoucher(id, input), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<VoucherResponseDto> delete(@PathVariable Long id) {
        return new ResponseEntity<>(voucherService.updateVoucherStatus(id, StatusEnum.DELETED), HttpStatus.OK);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<VoucherResponseDto> updateStatus(@PathVariable Long id, @RequestBody StatusEnum status) {
        return new ResponseEntity<>(voucherService.updateVoucherStatus(id, status), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherResponseDto> getById(@PathVariable Long id){
        return new ResponseEntity<>(voucherService.getVoucher(id), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<?> findAllProducts(
            @RequestParam(value = "page", defaultValue = Constant.DEFAULT_PAGE) int page,
            @RequestParam(value = "sortBy", defaultValue = "ID") SortBy sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "size", defaultValue = Constant.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "status", required = false) StatusEnum status,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        text = BusinessCommon.validInputData(text);
        Sort sort = Sort.by(direction, sortBy.field);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return new ResponseEntity<>(voucherService.findAllVoucher(text, startDate, endDate, status, pageable), HttpStatus.OK);
    }

}
