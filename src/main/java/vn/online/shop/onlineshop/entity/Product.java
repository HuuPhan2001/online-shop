package vn.online.shop.onlineshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.online.shop.onlineshop.common.config.SecurityContext;

import java.io.Serial;
import java.util.*;

@Entity
@Table(name = "product", schema = "online_shop")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseModel{
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    private String description;

    private String avatar;

    private String slug;

    private Double price;

    private Integer quantity;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            schema = "online_shop",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @ManyToMany
    @JoinTable(
            name = "product_voucher",
            schema = "online_shop",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "voucher_id")
    )
    private List<Voucher> vouchers;

    @PrePersist
    public void onCreate() {
        slug = UUID.randomUUID().toString();
    }

}
