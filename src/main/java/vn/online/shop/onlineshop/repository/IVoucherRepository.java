package vn.online.shop.onlineshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.online.shop.onlineshop.common.config.StatusEnum;
import vn.online.shop.onlineshop.entity.Voucher;

import java.util.Date;

@Repository
public interface IVoucherRepository extends IRepository<Voucher> {
    boolean existsByCodeAndStatusAndEndDateAfter(String code, StatusEnum status, Date endDate);

    @Query("SELECT v FROM Voucher v WHERE v.status <> 'DELETED' " +
            " AND (:text IS NULL OR LOWER(v.code) LIKE %:text%) " +
            " AND (COALESCE(:startDate, NULL) IS NULL OR v.startDate >= :startDate) " +
            " AND (COALESCE(:endDate, NULL) IS NULL OR v.endDate <= :endDate) " +
            " AND (:status IS NULL OR v.status = :status)")
    Page<Voucher> findAllVoucher(String text, Date startDate, Date endDate, StatusEnum status,Pageable pageable);
}
