package vn.online.shop.onlineshop.repository;

import org.springframework.stereotype.Repository;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.entity.CategoryType;

@Repository
public interface ICategoryTypeRepository extends IRepository<CategoryType>{
    CategoryType findByCodeAndStatus(String name, StatusEnum status);
}
