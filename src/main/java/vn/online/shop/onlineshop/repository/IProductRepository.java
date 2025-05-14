package vn.online.shop.onlineshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.entity.Product;

import java.util.List;

@Repository
public interface IProductRepository extends IRepository<Product>{
    @Query("SELECT p FROM Product p WHERE p.status <> 'DELETED' " +
            " AND (:text IS NULL OR LOWER(p.name) LIKE %:text%) " +
            " AND (:status IS NULL OR p.status = :status) " +
            " AND (COALESCE(:categoryIds, NULL) IS NULL OR :categoryIds IN (SELECT ct.id FROM p.categories ct)) " +
            " AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            " AND (:maxPrice IS NULL OR p.price <= :maxPrice) ")
    Page<Product> findAllWithPage(String text, StatusEnum status, List<Long> categoryIds, Double minPrice, Double maxPrice, Pageable pageable);
}
