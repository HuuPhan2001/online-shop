package vn.online.shop.onlineshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.entity.CategoryType;
import vn.online.shop.onlineshop.service.CategoryTypeService;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category-type")
public class CategoryTypeController {
    private final CategoryTypeService categoryTypeService;

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody CategoryType input) {
        return new ResponseEntity<>(categoryTypeService.save(input), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CategoryType input) {
        CategoryType data = categoryTypeService.findByIdAndStatus(id, StatusEnum.ACTIVE);
        if (data != null) {
            input.setId(data.getId());
        }
        data = categoryTypeService.save(input);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listAll(){
        return new ResponseEntity<>(categoryTypeService.findAll(), HttpStatus.OK);
    }

}
