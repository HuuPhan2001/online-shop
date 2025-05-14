package vn.online.shop.onlineshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.online.shop.onlineshop.common.config.BusinessCommon;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.common.mapper.CartMapper;
import vn.online.shop.onlineshop.entity.Cart;
import vn.online.shop.onlineshop.exception.RestExceptionHandler;
import vn.online.shop.onlineshop.repository.ICartItemRepository;
import vn.online.shop.onlineshop.repository.ICartRepository;
import vn.online.shop.onlineshop.repository.IRepository;
import vn.online.shop.onlineshop.service.dto.CartRequestDto;
import vn.online.shop.onlineshop.service.dto.CartResponseDto;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService extends BaseService<Cart> {
    private final ICartRepository cartRepository;
    private final ICartItemRepository itemRepository;
    private final CartMapper cartMapper;

    @Override
    public IRepository<Cart> getRepository() {
        return cartRepository;
    }

    public CartResponseDto createCart(CartRequestDto dto) {
        if(cartRepository.existsByUserIdAndStatus(dto.getUserId(), StatusEnum.ACTIVE)) {
            throw new RestExceptionHandler(Constant.EXIST_CART);
        }
        Cart cart = cartMapper.toEntity(dto);
        cart = cartRepository.save(cart);
        return cartMapper.toResponseDto(cart);
    }

    public CartResponseDto updateCart(Long id ,CartRequestDto dto) {
        Cart cart = cartRepository.findByIdAndNotDeleted(id);
        if(Objects.isNull(cart)){
            throw new RestExceptionHandler(Constant.NOT_FOUND_CART);
        }
        cart = cartMapper.partialUpdate(dto, cart);
        cart = cartRepository.save(cart);
        return cartMapper.toResponseDto(cart);
    }

    public CartResponseDto updateCartStatus(Long id, StatusEnum status) {
        Cart cart = cartRepository.findByIdAndNotDeleted(id);
        if(Objects.isNull(cart)){
            throw new RestExceptionHandler(Constant.NOT_FOUND_CART);
        }
        if(cart.getStatus() == StatusEnum.ACTIVE){

        }
        cart = BusinessCommon.validateUpdateStatus(status, cart);
        return cartMapper.toResponseDto(cartRepository.save(cart));
    }

    public boolean deleteCart(Long id) {
        Cart cart = cartRepository.findByIdAndNotDeleted(id);
        if(Objects.isNull(cart)){
            throw new RestExceptionHandler(Constant.NOT_FOUND_CART);
        }
        try {
            cartRepository.delete(cart);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RestExceptionHandler(Constant.ACTION_FAILED);
        }
        return true;
    }

    public CartResponseDto getCart(Long id) {
        Cart cart = cartRepository.findByIdAndNotDeleted(id);
        return cartMapper.toResponseDto(cart);
    }
}
