package com.matager.app.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@Validated
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SyncReservationModel {
    private String storeUuid;
    private List<ReservationModel> reservations;
}
