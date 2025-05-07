package vn.online.shop.onlineshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category", schema = "online_shop")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseModel {

    @Column(name = "category_type_id")
    private Long categoryTypeId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_type_id", updatable = false, insertable = false)
    private CategoryType categoryType;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    private String description;

    private Long parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent", updatable = false, insertable = false)
    private Category parentCate;

    private String code;

    @Column(name = "is_default")
    private boolean isDefault;
}
