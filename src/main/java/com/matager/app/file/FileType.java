package com.matager.app.file;

public enum FileType {
    GENERAL("general/"), ITEM_PHOTO("images/item/"),
    CATEGORY_PHOTO("images/category/"),
    CATEGORY_ICON("images/category_icon/");

    String prefix;

    FileType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
