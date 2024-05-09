/*
 * @Abdullah Sallam
 */

/*
 * @Abdullah Sallam
 */

package com.matager.app.common.statistics.dto;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class StoreItemSaleDto {
    String store;
    Long id;
    String name;
    Double count; // count here is a Double because you can sell 0.5 of an item
    Double amount;
}
