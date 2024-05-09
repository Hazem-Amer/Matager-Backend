package com.matager.app.common.statistics.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StoreDateAmountDto {
    private String store;
    private String date;
    private Double amount;
}
