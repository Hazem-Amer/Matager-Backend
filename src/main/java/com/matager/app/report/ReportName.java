package com.matager.app.report;

import lombok.Getter;

@Getter
public enum ReportName {
    // General
    DAILY("Daily Report"), DAILY_SEVERAL("Daily Several Days Report"), INTERVAL("Interval Report"),
    DAILY_TOTALS("Daily Totals Report"), DAILY_TOTALS_PDF("Daily Totals (PDF) Report"), INVITATION_STAFF_SHRINKAGE("Invitation Staff Shrinkage Report"),

    // Sales By Groups
    SALES_BY_CATEGORY_REPORT("Sales By Category Report"), SALES_BY_SUBCATEGORY_REPORT("Sales By Subcategory Report"), SALES_BY_PRODUCT_GROUP("Sales By Product Group Report"),

    // Item Sales, should be by subcategory by default
    ITEM_SALES("Item Sales Report"), ITEM_SALES_PDF("Item Sales (PDF) Report"),

    // Payment
    PAYMENT("Payments Report"), FOREIGN_CASH("Foreign Cash Report"),

    // Delivery
    DELIVERY_DAILY("Delivery Daily Report"), NEW_DELIVERY_CUSTOMERS("New Delivery Customer Report"),
    TOP_50_DELIVERY_CUSTOMERS("Top 50 Delivery Customer Report"), DELIVERY_ZONES("Delivery Zones Report"),

    // Cashbook
//    CASHBOOK("Cashbook Report"), TODO: will be available after options
    CASHBOOK_SUMMARY("Cashbook Summary Report"),

    // Stock Reports
    STOCK_TAKE_PDF("Stock Take (PDF) Report"), // TODO: has errors on POS

    // Others
    BOOKING_TO_CUSTOMER("Booking To Customer Report"), BOOKING_TO_CUSTOMER_OPEN_PDF("Booking To Customer Open Invoices (PDF) Report"), ORDER_CAPTAINS("Order Captains Report"),;

    private String name;

    ReportName(String name) {
        this.name = name;
    }

    // comma separated options
//    ReportName(String name, String options) {
//    }
}
