/*
 * @Abdullah Sallam
 */

package com.matager.app.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import java.time.LocalDateTime;

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
                @Index(name = "idx_order_invoice_no", columnList = "invoice_no"),
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

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Payment> payments;

    @Column(name = "invoice_no")
    private Long invoiceNo; // RechnungNr

    @Column(name = "cashier_name")
    private String cashierName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private Level level; // Ebene

    @Column(name = "sub_level")
    private String subLevel; // User created levels 'if level = Ebenen' like Garden, OutDoor, Indoor and so on...

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "order_captain_name")
    private String orderCaptainName;

    @Column(name = "is_cancelled")
    private Boolean isCancelled;

    @Column(name = "total")
    private Double total;

    @Override
    public String toString() {
        return "Order{" +
                "invoiceNo=" + invoiceNo +
                ", cashierName='" + cashierName + '\'' +
                ", createdAt=" + createdAt +
                ", level=" + level +
                ", subLevel='" + subLevel + '\'' +
                ", tableName='" + tableName + '\'' +
                ", orderCaptainName='" + orderCaptainName + '\'' +
                ", isCancelled=" + isCancelled +
                ", total=" + total +
                '}';
    }
}
