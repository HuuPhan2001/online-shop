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
import vn.online.shop.onlineshop.service.AuthenticationService;
import vn.online.shop.onlineshop.service.UserService;
import vn.online.shop.onlineshop.service.dto.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/user")
public class UserController {
    enum SortBy {
        UPDATE_AT("updateAt"), NAME("name"), ID("id");

        private String field;

        private SortBy(String field) {
            this.field = field;
        }
    }

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/create")
    public ResponseEntity<AuthResponse> create(@RequestBody @Valid RegisterRequest registerRequest) {
        return new ResponseEntity<>(authenticationService.register(registerRequest), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody @Valid RegisterRequest input) {
        return new ResponseEntity<>(userService.updateUser(id, input), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserResponse> delete(@PathVariable Long id) {
        return new ResponseEntity<>(userService.updateStatusUser(id, StatusEnum.DELETED), HttpStatus.OK);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<UserResponse> updateStatus(@PathVariable Long id, @RequestBody StatusEnum status) {
        return new ResponseEntity<>(userService.updateStatusUser(id, status), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<?> findAllUser(
            @RequestParam(value = "page", defaultValue = Constant.DEFAULT_PAGE) int page,
            @RequestParam(value = "sortBy", defaultValue = "ID") SortBy sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "size", defaultValue = Constant.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "status", required = false) StatusEnum status,
            @RequestParam(value = "roleId", required = false) Long roleId) {
        text = BusinessCommon.validInputData(text);
        Sort sort = Sort.by(direction, sortBy.field);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return new ResponseEntity<>(userService.findAllUser(text, status, roleId,pageable), HttpStatus.OK);
    }
}
