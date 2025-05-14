package vn.online.shop.onlineshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.service.CartService;
import vn.online.shop.onlineshop.service.dto.CartRequestDto;
import vn.online.shop.onlineshop.service.dto.CartResponseDto;
import vn.online.shop.onlineshop.service.dto.CategoryDto;
import vn.online.shop.onlineshop.service.dto.CategoryRequestDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/create")
    public ResponseEntity<CartResponseDto> create(@RequestBody @Valid CartRequestDto input) {
        return new ResponseEntity<>(cartService.createCart(input), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CartResponseDto> update(@PathVariable Long id ,@RequestBody @Valid CartRequestDto input) {
        return new ResponseEntity<>(cartService.updateCart(id, input), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return new ResponseEntity<>(cartService.deleteCart(id), HttpStatus.OK);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<CartResponseDto> updateStatus(@PathVariable Long id, @RequestBody StatusEnum status) {
        return new ResponseEntity<>(cartService.updateCartStatus(id, status), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDto> getCart(@PathVariable Long id) {
        return new ResponseEntity<>(cartService.getCart(id), HttpStatus.OK);
    }
}
