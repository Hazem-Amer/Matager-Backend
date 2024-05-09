/*
 * @Omar Elbeltagui
 */
package com.matager.app.common.statistics.dto;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class ItemSaleDto {
    Long id;
    String name;
    Double amount;
    Double count; // count here is a Double because you can sell 0.5 of an item
}
