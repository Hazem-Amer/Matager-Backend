package com.matager.app.common.statistics.dto.general;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class DateNameCountDto {
    String date;
    String name;
    Integer count;
}
