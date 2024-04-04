/*
 * @Abdullah Sallam
 */

package com.matager.app.common.exception.DB;

public class NoDBShardFound extends RuntimeException {
    public NoDBShardFound() {
        super("No DB Shard Found");
    }

    public NoDBShardFound(String message) {
        super(message);
    }

    public NoDBShardFound(String message, Throwable cause) {
        super(message, cause);
    }
}
