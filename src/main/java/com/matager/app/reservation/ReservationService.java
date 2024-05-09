package com.matager.app.reservation;

import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.user.User;

import java.util.List;

public interface ReservationService {


    List<Reservation> syncReservations(User user, SyncReservationModel reservationsModel);

    Reservation saveReservation(User user, ReservationModel newReservation);

    Reservation saveReservation(Owner owner, User user, Store store, ReservationModel newReservation);

    Reservation updateReservation(Owner owner, User user, Store store, ReservationModel reservationModel);
}
