package vn.online.shop.onlineshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

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

}
