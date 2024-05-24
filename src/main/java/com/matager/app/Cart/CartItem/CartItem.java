package com.matager.app.Cart.CartItem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.matager.app.Cart.Cart;
import com.matager.app.Item.Item;
import com.matager.app.common.domain.BaseEntity;
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
@Table(name = "cart_item")
@JsonIgnoreProperties({"cart", "user"})
public class CartItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @JsonIgnoreProperties({
            "hibernateLazyInitializer",
            "handler",
            "owner",
            "store",
            "itemNo",
            "itemName",
            "maximumOrderQuantity",
            "minimumOrderQuantity",
            "quantity",
            "skuNumber",
            "category",
            "subcategory",
            "weight",
            "costPrice",
            "isVisible"
    })
    private Item item;

    @Column(name = "name")
    private String name;

    @Column(name = "list_price")
    private Double listPrice;

    @Column(name = "quantity")
    private int quantity;
}