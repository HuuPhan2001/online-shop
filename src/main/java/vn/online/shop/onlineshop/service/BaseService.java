package vn.online.shop.onlineshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.entity.BaseModel;
import vn.online.shop.onlineshop.repository.IRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T extends BaseModel> implements IService<T> {
    public abstract IRepository<T> getRepository();

    @Override
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public List<T> saveAll(Iterable<T> entities) {
        return getRepository().saveAll(entities);
    }

    @Override
    public T getOne(Long id) {
        return getRepository().getOne(id);
    }

    @Override
    public Optional<T> findById(Long id) {
        return getRepository().findById(id);
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public List<T> findAllByStatus(StatusEnum status) {
        return getRepository().findAllByStatus(status);
    }

    @Override
    public Page<T> findAllPage(StatusEnum status, Pageable pageable) {
        return getRepository().findAllByStatus(status, pageable);
    }

    @Override
    public Optional<T> findByIdAndStatus(Long id, StatusEnum status) {
        return getRepository().findByIdAndStatus(id, status);
    }

    @Override
    public void deleteById(Long id) {
        getRepository().deleteById(id);
    }

    @Override
    public void delete(T entity) {
        getRepository().delete(entity);
    }
}
