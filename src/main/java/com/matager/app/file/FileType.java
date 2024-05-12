package com.matager.app.file;

public enum FileType {
    GENERAL("general/"), ITEM_PHOTO("photos/item"),
    CATEGORY_PHOTO("photos/category");

    String prefix;

    FileType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
