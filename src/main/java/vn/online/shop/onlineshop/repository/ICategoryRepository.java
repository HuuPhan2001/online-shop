package vn.online.shop.onlineshop.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.entity.Category;

import java.util.List;

@Repository
public interface ICategoryRepository extends IRepository<Category> {
    @Query("select c from Category c where ((:type is null) or c.categoryTypeId = (:type)) and c.name = :name")
    List<Category> findByNameAndCategoryTypeId(String name, Long type);

    @Query("SELECT c FROM Category c WHERE c.categoryTypeId = :typeId AND c.status = :status AND c.isDefault IS TRUE")
    List<Category> findByCategoryTypeIdAndStatusWithDefault(Long typeId, StatusEnum status);

    @Query("SELECT c FROM Category c WHERE c.id = :id AND c.status <> 'DELETED'")
    Category findCategoryByIdNotDeleted(Long id);

//    @Query("SELECT c FROM Category c WHERE c.status <> 'DELETED' " +
//            " AND (:typeId IS NULL OR c.categoryTypeId = :typeId) " +
//            " AND (:text IS NULL OR ((LOWER(c.code) LIKE %||:text||%) OR (LOWER(c.)))) )")
//    List<Category> listCategory();
}
