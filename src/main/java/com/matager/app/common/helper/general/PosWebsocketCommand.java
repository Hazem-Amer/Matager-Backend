package com.matager.app.common.helper.general;

import lombok.Getter;

@Getter
public enum PosWebsocketCommand {
    GET_REPORT("wshttp_get_report");

    private final String command;

    PosWebsocketCommand(String command) {
        this.command = command;
    }

}
