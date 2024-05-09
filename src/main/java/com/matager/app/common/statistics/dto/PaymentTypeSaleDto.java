/*
 * @Abdullah Sallam
 */

package com.matager.app.common.statistics.dto;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class PaymentTypeSaleDto {
    String name;
    String paymentType;
    Double amount;
}
