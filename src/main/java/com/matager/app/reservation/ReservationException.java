package com.matager.app.reservation;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class ReservationException extends RuntimeException {

    public ReservationException() {
        super();
    }

    public ReservationException(Long reservationNo, String message) {
        super("Error saving reservation with reservationNo:" + reservationNo + "\n message: " + message);
    }

    public ReservationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationException(String message) {
        super(message);
    }

    public ReservationException(Throwable cause) {
        super(cause);
    }

}
