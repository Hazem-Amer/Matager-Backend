/*
 * @Abdullah Sallam
 */

package com.matager.app.Item;

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

    @Column(name = "item_no")
    private Long itemNo; // ArtikelNr

    @Column(name = "item_name")
    private String itemName; // ArtikelName

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "purchase_price")
    private Double purchasePrice;

    @Column(name = "list_price")
    private Double listPrice; // Selling price

    @Column(name = "product_group")
    private String productGroup;

    @Column(name = "category")
    private String category;

    @Column(name = "subcategory")
    private String subcategory;

    @Column(name = "unit")
    private String unit;

    @Column(name = "amount")
    private Double amount = 0.00; // Stock level

    @Column(name = "is_sale")
    private boolean isSale;

    @Column(name = "minimum_stock_level")
    private Double minimumStockLevel = 0.00;

    @Column(name = "default_supplier")
    private String defaultSupplier;
}


