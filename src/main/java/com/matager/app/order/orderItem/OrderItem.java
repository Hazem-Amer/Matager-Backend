/*
 * @Abdullah Sallam
 */

package com.matager.app.order.orderItem;

import at.orderking.bossApp.Item.Item;
import at.orderking.bossApp.common.domain.BaseEntity;
import at.orderking.bossApp.order.Order;
import at.orderking.bossApp.owner.Owner;
import at.orderking.bossApp.store.Store;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"id", "owner", "store"})
@Table(name = "order_item")
public class OrderItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private Owner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // if item is deleted these props are IMPORTANT
    @Column(name = "item_no")
    private Long itemNo; // ArtikelNr

    @Column(name = "item_name")
    private String itemName; // ArtikelName
    // --------------------------------------------
    @Column(name = "price")
    private Double price;

    @Column(name = "list_price")
    private Double listPrice; // Price without discount

    @Column(name = "discount")
    private Double discount;

    @Column(name = "count")
    private Double count;

    @Column(name = "vat")
    private Double vat;

    @Column(name = "product_group")
    private String productGroup;

    @Column(name = "zero_price_reason")
    @Enumerated(EnumType.STRING)
    private ZeroPriceReason zeroPriceReason; // RechnungDetails.Art, (0: normal), (1: staff), (2: invitations), (3: shrinkage, damaged)

}
