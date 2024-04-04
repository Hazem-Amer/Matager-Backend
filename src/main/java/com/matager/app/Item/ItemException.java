package com.matager.app.Item;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class ItemException extends RuntimeException {

    public ItemException() {
        super();
    }

    public ItemException(Long itemNo, String message) {
        super("Error saving item with itemNo:" + itemNo + "\n message: " + message);
    }

    public ItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemException(String message) {
        super(message);
    }

    public ItemException(Throwable cause) {
        super(cause);
    }

}
