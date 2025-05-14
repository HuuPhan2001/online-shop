package vn.online.shop.onlineshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.online.shop.onlineshop.common.config.BusinessCommon;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.service.ProductService;
import vn.online.shop.onlineshop.service.dto.CategoryDto;
import vn.online.shop.onlineshop.service.dto.CategoryRequestDto;
import vn.online.shop.onlineshop.service.dto.ProductRequestDto;
import vn.online.shop.onlineshop.service.dto.ProductResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    enum SortBy {
        UPDATE_AT("updateAt"), NAME("name"), ID("id"), PRICE("price");

        private String field;

        private SortBy(String field) {
            this.field = field;
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/page")
    public ResponseEntity<?> findAllProducts(
            @RequestParam(value = "page", defaultValue = Constant.DEFAULT_PAGE) int page,
            @RequestParam(value = "sortBy", defaultValue = "ID") SortBy sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "size", defaultValue = Constant.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "status", required = false) StatusEnum status,
            @RequestParam(value = "categoryIds", required = false) List<Long> categoryIds,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice) {
        text = BusinessCommon.validInputData(text);
        Sort sort = Sort.by(direction, sortBy.field);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return new ResponseEntity<>(productService.findAllWithPage(text, status, categoryIds, minPrice, maxPrice, pageable), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponseDto> create(@RequestBody @Valid ProductRequestDto input) {
        return new ResponseEntity<>(productService.createProduct(input), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponseDto> update(@PathVariable Long id ,@RequestBody @Valid ProductRequestDto input) {
        return new ResponseEntity<>(productService.updateProduct(id, input), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductResponseDto> delete(@PathVariable Long id) {
        return new ResponseEntity<>(productService.updateStatus(id, StatusEnum.DELETED), HttpStatus.OK);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<ProductResponseDto> updateStatus(@PathVariable Long id, @RequestBody StatusEnum status) {
        return new ResponseEntity<>(productService.updateStatus(id, status), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable Long id){
        return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
    }
}
