/*
 * @Abdullah Sallam
 */

package com.matager.app.order.model;

import com.matager.app.order.orderItem.ZeroPriceReason;
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
    private Double quantity;
    private Double totalPrice;
    private Double listPrice; //price per 1
    private Double discount;
    private Double tax;
}
