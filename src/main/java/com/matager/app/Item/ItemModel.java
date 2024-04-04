package com.matager.app.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ItemModel {

    private String storeUuid;

    private Long itemNo; // ArtikelNr
    private String name; // ArtikelName
    private String iconUrl;
    private Double purchasePrice;
    private Double listPrice; // Selling price
    private String productGroup;
    private String category;
    private String subcategory;
    private String unit;
    private Double amount; // Stock level
    private boolean isSale;
    private Double minimumStockLevel;
    private String defaultSupplier;

}
