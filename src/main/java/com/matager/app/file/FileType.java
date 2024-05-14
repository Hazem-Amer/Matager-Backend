package com.matager.app.file;

public enum FileType {
    GENERAL("general/"),
    STORE_ICON("images/store/"),
    ITEM_IMAGE("images/item/"),
    CATEGORY_IMAGE("images/category/"),
    CATEGORY_ICON("images/category_icon/"),
    SUB_CATEGORY_ICON("images/sub_category_icon/");
    String prefix;

    FileType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
