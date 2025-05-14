package vn.online.shop.onlineshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.online.shop.onlineshop.common.config.BusinessCommon;
import vn.online.shop.onlineshop.common.config.Constant;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.common.mapper.ProductMapper;
import vn.online.shop.onlineshop.entity.Product;
import vn.online.shop.onlineshop.exception.RestExceptionHandler;
import vn.online.shop.onlineshop.repository.IProductRepository;
import vn.online.shop.onlineshop.repository.IRepository;
import vn.online.shop.onlineshop.service.dto.ProductRequestDto;
import vn.online.shop.onlineshop.service.dto.ProductResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService extends BaseService<Product> {
    private final IProductRepository productRepository;
    private final ProductMapper productMapper;


    @Override
    public IRepository<Product> getRepository() {
        return productRepository;
    }

    public Page<ProductResponseDto> findAllWithPage(String text, StatusEnum status, List<Long> categoryIds, Double minPrice, Double maxPrice, Pageable pageable) {
        Page<Product> products = productRepository.findAllWithPage(text, status, categoryIds, minPrice, maxPrice, pageable);
        List<ProductResponseDto> productResponseDtos = products.getContent().stream().map(productMapper::toResponse).toList();
        return new PageImpl<>(productResponseDtos, products.getPageable(), products.getTotalElements());
    }

    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = productMapper.toEntity(productRequestDto);
        product = productRepository.save(product);
        return productMapper.toResponse(product);
    }

    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) {
        Product product = productRepository.findByIdAndStatus(id, StatusEnum.ACTIVE)
                .orElseThrow(() -> new RestExceptionHandler(Constant.NOT_FOUND_PRODUCT));
        productMapper.partialUpdate(productRequestDto, product);
        return productMapper.toResponse(productRepository.save(product));
    }

    public ProductResponseDto updateStatus(Long id, StatusEnum status) {
        Product product = productRepository.findByIdAndNotDeleted(id);
        if(Objects.isNull(product)){
            throw new RestExceptionHandler(Constant.NOT_FOUND_PRODUCT);
        }
        product = productRepository.save(BusinessCommon.validateUpdateStatus(status, product));
        return productMapper.toResponse(product);
    }

    public ProductResponseDto getById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new RestExceptionHandler(Constant.NOT_FOUND_PRODUCT));
        return productMapper.toResponse(product);
    }
}
