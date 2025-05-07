package vn.online.shop.onlineshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.entity.BaseModel;

import java.util.List;

@NoRepositoryBean
public interface IRepository<T extends BaseModel> extends JpaRepository<T, Long> {
    T findByIdAndStatus(Long id, StatusEnum status);

    List<T> findAllByStatus(StatusEnum status);

    Page<T> findAllByStatus(StatusEnum status, Pageable pageable);
}
