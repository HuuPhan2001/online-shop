package vn.online.shop.onlineshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import vn.online.shop.onlineshop.common.config.SecurityContext;
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
        this.createAt = new Date();
        this.updateAt = new Date();
        User user = SecurityContext.getCurrentUser();
        if (user != null) {
            this.createBy = user.getId();
            this.updateBy = this.createBy;
        } else{
            this.createBy = 1L;
            this.updateBy = 1L;
        }
        if(this.status == null){
            this.status = StatusEnum.ACTIVE;
        }
    }

    @PreUpdate
    public void addUpdateByAndDate() {
        this.updateAt = new Date();
        User user = SecurityContext.getCurrentUser();
        if (user != null) {
            this.updateBy = user.getId();
        }
    }
}
