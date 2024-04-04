package com.matager.app.reservation;

import at.orderking.bossApp.owner.Owner;
import at.orderking.bossApp.store.Store;
import at.orderking.bossApp.user.User;

import java.util.List;

public interface ReservationService {


    List<Reservation> syncReservations(User user, SyncReservationModel reservationsModel);

    Reservation saveReservation(User user, ReservationModel newReservation);

    Reservation saveReservation(Owner owner, User user, Store store, ReservationModel newReservation);

    Reservation updateReservation(Owner owner, User user, Store store, ReservationModel reservationModel);
}
