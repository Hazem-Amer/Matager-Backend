package com.matager.app.order.delivery;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class DeliveryCustomerException extends RuntimeException {

    public DeliveryCustomerException() {
        super();
    }

    public DeliveryCustomerException(Long customerNo, String message) {
        super("Error saving customer with customerNo:" + customerNo + "\n message: " + message);
    }

    public DeliveryCustomerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeliveryCustomerException(String message) {
        super(message);
    }

    public DeliveryCustomerException(Throwable cause) {
        super(cause);
    }

}
