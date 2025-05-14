package vn.online.shop.onlineshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.entity.BaseModel;
import vn.online.shop.onlineshop.repository.IRepository;

import java.util.List;
import java.util.Optional;

public interface IService<T extends BaseModel>  {
    T save(T entity);

    List<T> saveAll(Iterable<T> entities);

    T getOne(Long id);

    Optional<T> findById(Long id);

    Optional<T> findByIdAndStatus(Long id, StatusEnum status);

    List<T> findAll();

    List<T> findAllByStatus(StatusEnum status);

    Page<T> findAllPage(StatusEnum status, Pageable pageable);

    void delete(T entity);

    void deleteById(Long id);
}
