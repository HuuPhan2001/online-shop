package vn.online.shop.onlineshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.common.mapper.CategoryMapper;
import vn.online.shop.onlineshop.entity.Category;
import vn.online.shop.onlineshop.entity.CategoryType;
import vn.online.shop.onlineshop.exception.RestExceptionHandler;
import vn.online.shop.onlineshop.exception.RestFieldExceptionHandler;
import vn.online.shop.onlineshop.repository.ICategoryRepository;
import vn.online.shop.onlineshop.repository.ICategoryTypeRepository;
import vn.online.shop.onlineshop.repository.IRepository;
import vn.online.shop.onlineshop.service.dto.CategoryCreateDto;
import vn.online.shop.onlineshop.service.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService extends BaseService<Category> {

    private final ICategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ICategoryTypeRepository categoryTypeRepository;

    @Override
    public IRepository<Category> getRepository() {
        return categoryRepository;
    }

    public Category save(CategoryCreateDto input) {
        Category c = findByNameAndCategoryTypeId(input.getName().trim(), input.getCategoryTypeId());
        if (c != null) {
            throw new RestFieldExceptionHandler("name", Constant.EXIST_CATEGORY);
        }
        return categoryRepository.save(categoryMapper.toEntity(input));
    }

    public Category findByNameAndCategoryTypeId(String name, Long type) {
        List<Category> cList = categoryRepository.findByNameAndCategoryTypeId(name, type);
        return cList.isEmpty() ? null : cList.get(0);
    }

    public Category findCategoryDefault() {
        CategoryType categoryType = categoryTypeRepository.findByCodeAndStatus("ROLE", StatusEnum.ACTIVE);
        if (categoryType == null) {
            throw new RestExceptionHandler(Constant.NOT_FOUND_CATEGORY_TYPE);
        }
        List<Category> list = categoryRepository.findByCategoryTypeIdAndStatusWithDefault(categoryType.getId(), StatusEnum.ACTIVE);
        return list.isEmpty() ? null : list.get(0);
    }

    public CategoryDto updateCategory(Long id, CategoryCreateDto input) {
        Category existingCategory = findByIdAndStatus(id, StatusEnum.ACTIVE);
        if (existingCategory == null) {
            throw new RestExceptionHandler(Constant.NOT_FOUND_CATEGORY);
        }
        categoryMapper.partialUpdate(input, existingCategory);
        return categoryMapper.toResponse(categoryRepository.save(existingCategory));
    }

    public CategoryDto updateStatus(Long id, StatusEnum status) {
        Category category = categoryRepository.findCategoryByIdNotDeleted(id);
        if (category == null) {
            throw new RestExceptionHandler(Constant.NOT_FOUND_CATEGORY);
        }
        switch (status) {
            case ACTIVE:
                if (category.getStatus() == StatusEnum.ACTIVE) {
                    throw new RestExceptionHandler(Constant.INVALID_DATA);
                }
                category.setStatus(status);
                break;
            case INACTIVE:
                if (category.getStatus() == StatusEnum.INACTIVE) {
                    throw new RestExceptionHandler(Constant.INVALID_DATA);
                }
                category.setStatus(StatusEnum.INACTIVE);
                break;
            case DELETED:
                category.setStatus(StatusEnum.DELETED);
                break;
            default:
                throw new RestExceptionHandler(Constant.INVALID_DATA);
        }
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

}
