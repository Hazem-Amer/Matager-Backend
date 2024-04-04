package com.matager.app.reservation;

import at.orderking.bossApp.auth.AuthenticationFacade;
import at.orderking.bossApp.common.helper.res_model.ResponseModel;
import at.orderking.bossApp.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final AuthenticationFacade authenticationFacade;
    private final ReservationService reservationService;

    @PostMapping("/sync")
    public ResponseEntity<ResponseModel> syncReservations(@RequestBody @Valid SyncReservationModel reservationsModel) {
        User user = authenticationFacade.getAuthenticatedUser();

        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Reservation/s saved successfully.")
                        .data(Map.of("reservations", reservationService.syncReservations(user, reservationsModel)))
                        .build());
    }
}
