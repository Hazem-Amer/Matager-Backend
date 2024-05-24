package com.matager.app.wishlist.wishlist_item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.matager.app.Item.Item;
import com.matager.app.common.domain.BaseEntity;
import com.matager.app.wishlist.WishList;
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
@Table(name = "wish_list_item")
@JsonIgnoreProperties({"wishList"})
public class WishListItem extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wish_list_id")
    private WishList wishList;

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

}
