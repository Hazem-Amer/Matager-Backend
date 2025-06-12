/*
 * @Abdullah Sallam
 */

package com.matager.app.payment;


import java.util.List;

public interface PaymentService {

    List<Payment> getPayments(Long ownerId, Long storeId);
}
