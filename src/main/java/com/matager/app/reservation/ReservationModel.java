package com.matager.app.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Data
@Validated
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationModel {
    private Long reservationNo; // Reservierung ID column on the POS DB
    private String cashierName;
    private String reserverName; // Reservierung name column on the POS DB
    private String tableName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss[.SSS][.SS][.S]")
    private LocalDateTime from;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss[.SSS][.SS][.S]")
    private LocalDateTime to;
    private Integer persons;
}
