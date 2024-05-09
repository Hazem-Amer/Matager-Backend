/*
 * @Abdullah Sallam
 */

package com.matager.app.order.model;

import com.matager.app.payment.PaymentType;
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
public class PaymentModel {
    private PaymentType type;
    private String name;
    private Double amount;
}
