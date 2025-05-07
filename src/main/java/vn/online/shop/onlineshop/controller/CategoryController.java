package vn.online.shop.onlineshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.entity.Category;
import vn.online.shop.onlineshop.service.CategoryService;
import vn.online.shop.onlineshop.service.CategoryTypeService;
import vn.online.shop.onlineshop.service.IService;
import vn.online.shop.onlineshop.service.dto.CategoryCreateDto;

@RestController
@RequiredArgsConstructor
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

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody @Valid CategoryCreateDto input) {
        return new ResponseEntity<>(categoryService.save(input), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id ,@RequestBody @Valid CategoryCreateDto input) {
        return new ResponseEntity<>(categoryService.updateCategory(id, input), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.updateStatus(id, StatusEnum.DELETED), HttpStatus.OK);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody @Valid StatusEnum status) {
        return new ResponseEntity<>(categoryService.updateStatus(id, status), HttpStatus.OK);
    }

//    @GetMapping("/list")
//    public ResponseEntity<?> list() {
//        return new ResponseEntity<>(categoryService.findAllByStatus(), HttpStatus.OK);
//    }
}
