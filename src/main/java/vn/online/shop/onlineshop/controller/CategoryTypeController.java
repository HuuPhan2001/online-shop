package vn.online.shop.onlineshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.entity.CategoryType;
import vn.online.shop.onlineshop.service.CategoryTypeService;
import vn.online.shop.onlineshop.service.dto.CategoryTypeCreateDto;
import vn.online.shop.onlineshop.service.dto.CategoryTypeResponse;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/category-type")
public class CategoryTypeController {
    private final CategoryTypeService categoryTypeService;

    @PostMapping("/create")
    public ResponseEntity<CategoryTypeResponse> create(@RequestBody CategoryTypeCreateDto input) {
        return new ResponseEntity<>(categoryTypeService.create(input), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryTypeResponse> update(@PathVariable Long id, @RequestBody CategoryTypeCreateDto input) {
        return new ResponseEntity<>(categoryTypeService.update(id, input), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CategoryTypeResponse> delete(@PathVariable Long id) {
        return new ResponseEntity<>(categoryTypeService.updateStatus(id, StatusEnum.DELETED), HttpStatus.OK);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<CategoryTypeResponse> updateStatus(@PathVariable Long id, @RequestBody StatusEnum status) {
        return new ResponseEntity<>(categoryTypeService.updateStatus(id, status), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategoryType>> listAll() {
        return new ResponseEntity<>(categoryTypeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page/{status}")
    public ResponseEntity<Page<CategoryType>> pageAllCategoryType(
            @PathVariable StatusEnum status,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "size", defaultValue = Constant.DEFAULT_PAGE_SIZE) int size) {
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return new ResponseEntity<>(categoryTypeService.findAllPage(status, pageable), HttpStatus.OK);
    }

}
