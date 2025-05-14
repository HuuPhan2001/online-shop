package vn.online.shop.onlineshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.online.shop.onlineshop.common.config.BusinessCommon;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.common.mapper.UserMapper;
import vn.online.shop.onlineshop.entity.Category;
import vn.online.shop.onlineshop.entity.User;
import vn.online.shop.onlineshop.exception.RestExceptionHandler;
import vn.online.shop.onlineshop.repository.IRepository;
import vn.online.shop.onlineshop.repository.IUserRepository;
import vn.online.shop.onlineshop.service.dto.*;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService<User> {
    private final IUserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public IRepository<User> getRepository() {
        return userRepository;
    }

    public UserResponse updateUser(Long id, RegisterRequest registerRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RestExceptionHandler(Constant.NOT_FOUND_USER));

        if (registerRequest.getPassword() != null && !registerRequest.getPassword().isBlank()) {
            String encodedNewPassword = passwordEncoder.encode(registerRequest.getPassword());
            registerRequest.setPassword(encodedNewPassword);
        } else {
            registerRequest.setPassword(null);
        }

        user = userMapper.partialUpdate(registerRequest, user);

        user = userRepository.save(user);

        return userMapper.toResponse(user);
    }

    public UserResponse updateStatusUser(Long id, StatusEnum status) {
        User user = userRepository.findByIdAndNotDeleted(id);
        if (Objects.isNull(user)) {
            throw new RestExceptionHandler(Constant.NOT_FOUND_USER);
        }
        user = userRepository.save(BusinessCommon.validateUpdateStatus(status, user));
        return userMapper.toResponse(user);
    }

    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RestExceptionHandler(Constant.NOT_FOUND_USER));
        return userMapper.toResponse(user);
    }

    public Page<UserDto> findAllUser(String text, StatusEnum status, Long roleId, Pageable pageable) {
        Page<User> users = userRepository.findAllUser(text, status, roleId, pageable);
        List<UserDto> userList = users.getContent().stream().map(userMapper::toDto).toList();
        return new PageImpl<>(userList, users.getPageable(), users.getTotalElements());
    }
}
