/*
 * @Abdullah Sallam
 */

package com.matager.app.order.model;

import at.orderking.bossApp.order.orderItem.ZeroPriceReason;
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
public class OrderItemModel {
    private Long itemNo; // ArtikelNr
    private String itemName; // ArtikelName
    private Double count;
    private Double price;
    private Double listPrice;
    private Double discount;
    private Double vat;
    private String productGroup;
    private ZeroPriceReason zeroPriceReason; // RechnungDetails.Art, (0: normal), (1: staff), (2: invitations), (3: shrinkage, damaged)
}
