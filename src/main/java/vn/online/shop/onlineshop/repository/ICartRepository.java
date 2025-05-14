package vn.online.shop.onlineshop.repository;

import org.springframework.stereotype.Repository;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.entity.Cart;

@Repository
public interface ICartRepository extends IRepository<Cart> {
    boolean existsByUserIdAndStatus(Long userId, StatusEnum status);
}
