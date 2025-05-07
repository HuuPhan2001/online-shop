package vn.online.shop.onlineshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.Date;

@Entity
@Table(name = "voucher", schema = "online_shop")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Voucher extends BaseModel{
    @Serial
    private static final long serialVersionUID = 1L;

    private String code;

    private String description;

    @Column(name = "discount_amount")
    private Integer discountAmount;

    @Column(name = "min_order_amount")
    private Integer minOrderAmount;

    private Integer quantity;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "product_id")
    private Long productId;

}
