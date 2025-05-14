package vn.online.shop.onlineshop.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.online.shop.onlineshop.common.config.PaymentStatusEnum;

import java.io.Serial;
import java.util.UUID;

@Entity
@Table(name = "orders", schema = "online_shop")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseModel{
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "order_total")
    private Integer orderTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatusEnum paymentStatus;

    private String address;

    private String district;

    private String ward;

    @Column(name = "phone_receiver")
    private String phoneReceiver;

    @Column(name = "name_receiver")
    private String nameReceiver;

    @Column(name = "ship_cost")
    private Double shipCost;

    @Column(name = "payment_method_id")
    private Long paymentMethodId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id", insertable = false, updatable = false)
    private Category paymentMethod;

    private String province;

    @Column(name = "order_code")
    private String orderCode;

    private Integer discount;

    @PrePersist
    public void onCreate(){
        this.orderCode = UUID.randomUUID().toString();
    }
}
