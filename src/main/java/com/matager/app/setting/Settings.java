package com.matager.app.setting;

public enum Settings {

    SYSTEM_ID("SYSTEM_ID");

    private final String posKey;

    Settings(String posKey) {
        this.posKey = posKey;
    }

    public String getPosKey() {
        return posKey;
    }


}
