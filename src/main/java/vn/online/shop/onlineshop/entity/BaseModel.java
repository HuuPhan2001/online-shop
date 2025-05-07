package vn.online.shop.onlineshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import vn.online.shop.onlineshop.common.config.StatusEnum;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = { "createdDate","updatedDate", "createdBy", "updatedBy" })
public abstract class BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private Date createAt;
    private Date updateAt;
    private Long createBy;
    private Long updateBy;

    @PrePersist
    public void prePersist() {
        createAt = new Date();
        updateAt = new Date();
        createBy = 1L;
        updateBy = 1L;
        status = StatusEnum.ACTIVE;
    }
}
