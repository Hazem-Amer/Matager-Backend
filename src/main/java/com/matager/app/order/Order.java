/*
 * @Abdullah Sallam
 */

package com.matager.app.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.matager.app.common.domain.BaseEntity;
import com.matager.app.order.delivery.DeliveryOrder;
import com.matager.app.order.orderItem.OrderItem;
import com.matager.app.owner.Owner;
import com.matager.app.payment.Payment;
import com.matager.app.store.Store;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"id", "owner", "store"})
@Table(name = "`order`",
        indexes = {
                @Index(name = "idx_order_owner_id", columnList = "owner_id"),
                @Index(name = "idx_order_store_id", columnList = "store_id"),
                @Index(name = "idx_order_created_at", columnList = "created_at")
        }
)
public class Order extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private Owner owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    @JsonIgnore
    private Store store;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderItem> items;

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private DeliveryOrder deliveryOrder;

//    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<Payment> payments;

    @Column(name = "order_no")
    private Long orderNo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_cancelled")
    private Boolean isCancelled;

    @Column(name = "sub_total")
    private Double subTotal; // price of all orderItems without delivery,discount and Tax

    @Column(name = "total")
    private Double total;


}
