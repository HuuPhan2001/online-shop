package vn.online.shop.onlineshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends IRepository<User>{
    Optional<User> findByUsername(String username);

    boolean existsByUsernameOrEmail(String username, String email);

    @Query("SELECT u FROM User u WHERE u.status <> 'DELETE' " +
            " AND (:text IS NULL OR LOWER(u.username) LIKE %:text% OR LOWER(u.email) LIKE %:text% " +
            " OR LOWER(u.fullName) LIKE %:text% ) " +
            " AND (:status IS NULL OR u.status = :status) " +
            " AND (:roleId IS NULL OR u.roleId = :roleId)")
    Page<User> findAllUser(String text, StatusEnum status, Long roleId, Pageable pageable);
}
