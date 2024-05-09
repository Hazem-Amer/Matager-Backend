/*
 * @Abdullah Sallam
 */

package com.matager.app.order.delivery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.matager.app.common.domain.BaseEntity;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"id", "owner", "store"})
@Table(name = "delivery_customer")
public class DeliveryCustomer extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "customer_no")
    private Long customerNo; // kundenNr

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;
}
