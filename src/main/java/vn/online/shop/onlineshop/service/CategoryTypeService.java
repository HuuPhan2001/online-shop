package vn.online.shop.onlineshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.online.shop.onlineshop.entity.CategoryType;
import vn.online.shop.onlineshop.repository.ICategoryTypeRepository;
import vn.online.shop.onlineshop.repository.IRepository;

@Service
@RequiredArgsConstructor
public class CategoryTypeService extends BaseService<CategoryType>{
    private final ICategoryTypeRepository categoryTypeRepository;
    @Override
    public IRepository<CategoryType> getRepository() {
        return categoryTypeRepository;
    }
}
