package com.matager.app.owner.settings;

public enum OwnerSettings {
    DAY_START_TIME;


    public static String getDefaultValue(OwnerSettings setting) {
        switch (setting) {
            case DAY_START_TIME:
                return "06:00";
            default:
                return "";
        }
    }
}
