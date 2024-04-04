package com.matager.app.order;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class OrderException extends RuntimeException {

    public OrderException() {
        super();
    }

    public OrderException(Long invoiceNo, String message) {
        super("Error saving order with invoiceNo:" + invoiceNo + "\n message: " + message);
    }

    public OrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderException(String message) {
        super(message);
    }

    public OrderException(Throwable cause) {
        super(cause);
    }

}
