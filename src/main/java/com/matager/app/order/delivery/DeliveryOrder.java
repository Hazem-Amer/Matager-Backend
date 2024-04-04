/*
 * @Abdullah Sallam
 */

package com.matager.app.order.delivery;

import at.orderking.bossApp.common.domain.BaseEntity;
import at.orderking.bossApp.order.Order;
import at.orderking.bossApp.owner.Owner;
import at.orderking.bossApp.store.Store;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/* Every DeliveryOrder has an Order record,
 but this Entity for more details that are specific to delivery */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"id", "owner", "store"})
@Table(name = "delivery_order",
        indexes = {
                @Index(name = "idx_delivery_order_owner_id", columnList = "owner_id"),
                @Index(name = "idx_delivery_order_store_id", columnList = "store_id"),
                @Index(name = "idx_delivery_order_order_no", columnList = "order_no"),
                @Index(name = "idx_delivery_order_delivery_time", columnList = "delivery_time")
        }
)
public class DeliveryOrder extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_customer_id")
    private DeliveryCustomer deliveryCustomer;

    @Column(name = "invoice_no")
    private Long invoiceNo; // RechnungNr

    @Column(name = "order_no")
    private Long orderNo; // AuftargNr

    @Column(name = "source")
    private String source; // Tel, OnlineShop, Talabat etc...

    @Column(name = "driver_name")
    private String driverName;

    @Column(name = "zone_name")
    private String zoneName;

    @Column(name = "delivery_time")
    private LocalDateTime deliveryTime;

    @Column(name = "is_cancelled")
    private Boolean isCancelled;

    @Column(name = "is_delivered")
    private Boolean isDelivered;
}
