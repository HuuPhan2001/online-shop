package vn.online.shop.onlineshop.repository;

import org.springframework.stereotype.Repository;
import vn.online.shop.onlineshop.entity.User;

import java.util.Optional;

@Repository
public interface IUserRepository extends IRepository<User>{
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
