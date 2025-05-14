package vn.online.shop.onlineshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import vn.online.shop.onlineshop.service.CategoryService;
import vn.online.shop.onlineshop.service.CategoryTypeService;
import vn.online.shop.onlineshop.service.dto.CategoryRequestDto;
import vn.online.shop.onlineshop.service.dto.CategoryDto;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/categories")
public class CategoryController {
    enum SortBy {
        UPDATE_AT("updateAt"), NAME("name"), ID("id") ;

        private String field;

        private SortBy(String field) {
            this.field = field;
        }
    }

    private final CategoryService categoryService;

    private final CategoryTypeService categoryTypeService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> create(@RequestBody @Valid CategoryRequestDto input) {
        return new ResponseEntity<>(categoryService.create(input), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Long id ,@RequestBody @Valid CategoryRequestDto input) {
        return new ResponseEntity<>(categoryService.updateCategory(id, input), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CategoryDto> delete(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.updateStatus(id, StatusEnum.DELETED), HttpStatus.OK);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<CategoryDto> updateStatus(@PathVariable Long id, @RequestBody StatusEnum status) {
        return new ResponseEntity<>(categoryService.updateStatus(id, status), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<?> page(@RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "sortBy", defaultValue = "ID") SortBy sortBy,
                                  @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
                                  @RequestParam(value = "size", defaultValue = Constant.DEFAULT_PAGE_SIZE) int size,
                                  @RequestParam(value = "typeId", required = false) Long categoryTypeId,
                                  @RequestParam(value = "text", required = false) String text,
                                  @RequestParam(value = "ACTIVE", required = false) StatusEnum status) {
        text = BusinessCommon.validInputData(text);
        categoryTypeId = categoryTypeId != null && !categoryTypeId.toString().isEmpty() ? categoryTypeId : null;
        Sort sort = Sort.by(direction, sortBy.field);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return new ResponseEntity<>(categoryService.pageCategory(categoryTypeId, text, status, pageable), HttpStatus.OK);
    }
}
