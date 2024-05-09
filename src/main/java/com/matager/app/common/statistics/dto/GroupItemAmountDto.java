package com.matager.app.common.statistics.dto;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class GroupItemAmountDto {
    String group;
    String itemName;
    Double amount;
}
