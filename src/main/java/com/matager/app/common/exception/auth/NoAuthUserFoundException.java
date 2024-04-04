/*
 * @Abdullah Sallam
 */

package com.matager.app.common.exception.auth;

public class NoAuthUserFoundException extends RuntimeException {
    public NoAuthUserFoundException() {
        super("No Authenticated User Found");
    }

    public NoAuthUserFoundException(String message) {
        super(message);
    }

    public NoAuthUserFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
