package vn.online.shop.onlineshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;

@Entity
@Table(name = "cart_item", schema = "online_shop")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem extends BaseModel {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "cart_id")
    private Long cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
    private Cart cart;

    @Column(name = "product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    private Integer quantity;

    private String note;

    @Column(name = "unit_price")
    private Double unitPrice;
}
