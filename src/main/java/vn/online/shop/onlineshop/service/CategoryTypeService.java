package vn.online.shop.onlineshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.online.shop.onlineshop.common.config.BusinessCommon;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.common.mapper.CategoryTypeMapper;
import vn.online.shop.onlineshop.entity.CategoryType;
import vn.online.shop.onlineshop.exception.RestExceptionHandler;
import vn.online.shop.onlineshop.repository.ICategoryTypeRepository;
import vn.online.shop.onlineshop.repository.IRepository;
import vn.online.shop.onlineshop.service.dto.CategoryTypeCreateDto;
import vn.online.shop.onlineshop.service.dto.CategoryTypeResponse;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryTypeService extends BaseService<CategoryType> {
    private final ICategoryTypeRepository categoryTypeRepository;
    private final CategoryTypeMapper categoryTypeMapper;

    @Override
    public IRepository<CategoryType> getRepository() {
        return categoryTypeRepository;
    }

    public CategoryTypeResponse create(CategoryTypeCreateDto dto) {
        categoryTypeRepository.findByCodeNotDeleted(dto.getCode())
                .ifPresent(ct -> {
                    throw new RestExceptionHandler(Constant.EXIST_TYPE_CATEGORY);
                });

        return categoryTypeMapper.toResponse(categoryTypeRepository.save(categoryTypeMapper.toEntity(dto)));
    }

    public CategoryTypeResponse update(Long id, CategoryTypeCreateDto dto) {
        CategoryType existing = categoryTypeRepository.findById(id)
                .orElseThrow(() -> new RestExceptionHandler(Constant.NOT_FOUND_CATEGORY_TYPE));
        categoryTypeMapper.partialUpdate(dto, existing);
        return categoryTypeMapper.toResponse(categoryTypeRepository.save(existing));
    }

    public CategoryTypeResponse updateStatus(Long id, StatusEnum status) {
        CategoryType categoryType = categoryTypeRepository.findCategoryTypeByIdNotDeleted(id);
        if (Objects.isNull(categoryType)) {
            throw new RestExceptionHandler(Constant.NOT_FOUND_CATEGORY_TYPE);
        }
        if (!StatusEnum.ACTIVE.equals(status)) {
            boolean checkExistCategoryWithTypeId = categoryTypeRepository.existsCategoryUsingCategoryTypeId(id);
            if (checkExistCategoryWithTypeId) {
                throw new RestExceptionHandler(Constant.CATEGORY_TYPE_IN_USED);
            }
        }
        categoryType = categoryTypeRepository.save(BusinessCommon.validateUpdateStatus(status, categoryType));
        return categoryTypeMapper.toResponse(categoryTypeRepository.save(categoryType));
    }

    @Override
    public Page<CategoryType> findAllPage(StatusEnum status, Pageable pageable) {
        return categoryTypeRepository.findAllCategoryTypes(status, pageable);
    }
}
