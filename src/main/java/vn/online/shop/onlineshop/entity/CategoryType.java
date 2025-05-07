package vn.online.shop.onlineshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;

@Entity
@Table(name = "category_type", schema = "online_shop")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryType extends BaseModel{
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(length = 50)
    private String name;

    private String code;

    private String description;
}
