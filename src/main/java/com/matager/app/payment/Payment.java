/*
 * @Abdullah Sallam
 */

package com.matager.app.payment;

import com.matager.app.common.domain.BaseEntity;
import com.matager.app.order.Order;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"id", "owner", "store"})
@Table(name = "payment")
public class Payment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType; // CASH, CREDIT, ONLINE, COUPON

    @Column(name = "name")
    private String name; // Either (Company Supplier Of Payment), (Currency Of Cash), Or (Other Detail For Payment)

    @Column(name = "amount")
    private Double amount;
}
