/*
 * @Omar Elbeltagui
 */
package com.matager.app.common.statistics.dto.general;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class NameCountDto {
    String name;
    Integer count;
}
