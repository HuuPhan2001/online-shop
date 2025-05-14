package vn.online.shop.onlineshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.entity.CategoryType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICategoryTypeRepository extends IRepository<CategoryType>{
    CategoryType findByCodeAndStatus(String code, StatusEnum status);

    @Query("SELECT ct FROM CategoryType ct WHERE ct.code =:code AND ct.status <> 'DELETED'")
    Optional<CategoryType> findByCodeNotDeleted(String code);

    @Query("SELECT ct FROM CategoryType ct WHERE ct.id = :id AND ct.status <> 'DELETED'")
    CategoryType findCategoryTypeByIdNotDeleted(Long id);

    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.categoryTypeId =:id AND c.status <> 'DELETED'")
    boolean existsCategoryUsingCategoryTypeId(Long id);

    @Query("SELECT ct FROM CategoryType ct WHERE ct.status <> 'DELETED' " +
            " AND (:status IS NULL OR ct.status = :status)")
    Page<CategoryType> findAllCategoryTypes(StatusEnum status, Pageable pageable);
}
