/*
 * @Abdullah Sallam
 */

package com.matager.app.Item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.matager.app.ItemImage.ItemImage;
import com.matager.app.category.Category;
import com.matager.app.common.domain.BaseEntity;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.subcategory.SubCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"owner", "store"})
@Table(name = "item",
        indexes = {
                @Index(name = "idx_item_store_id", columnList = "store_id"),
                @Index(name = "idx_item_item_no", columnList = "item_no")
        }
)
public class Item extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id")
    private SubCategory subcategory;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.LAZY)
    private List<ItemImage> itemImages;

    @Column(name = "item_no")
    private Long itemNo;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "maximum_order_quantity")
    private Integer maximumOrderQuantity;

    @Column(name = "minimum_order_quantity")
    private Integer minimumOrderQuantity;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "sku_number", length = 50)
    private String skuNumber;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "list_price")
    private Double listPrice; // Selling price

    @Column(name = "cost_price")
    private Double costPrice;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_sale")
    private boolean isSale;

    @Column(name = "is_visible")
    private Boolean isVisible;

    @Column(name = "sold_times")
    private Integer soldTimes;

    @Column(name = "rating")
    private Double rating;


}


