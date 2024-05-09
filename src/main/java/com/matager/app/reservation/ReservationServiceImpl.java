package com.matager.app.reservation;

import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.store.StoreRepository;
import com.matager.app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final StoreRepository storeRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public List<Reservation> syncReservations(User user, SyncReservationModel reservationsModel) {
        Owner owner = user.getOwner();

        Store store;

        if (reservationsModel.getStoreUuid() != null) {
            store = storeRepository.findByOwnerIdAndUuid(owner.getId(), reservationsModel.getStoreUuid()).orElseThrow(() -> new RuntimeException("Store not found."));
        } else {
            if (user.getDefaultStore() == null)
                throw new RuntimeException("No default store found, please specify store uuid.");
            store = user.getDefaultStore();
        }

        List<Reservation> reservations = new ArrayList<>();

        for (ReservationModel reservationModel : reservationsModel.getReservations()) {
            if (reservationRepository.existsByStoreIdAndReservationNo(store.getId(), reservationModel.getReservationNo())) {
                reservations.add(updateReservation(owner, user, store, reservationModel));
            } else {
                reservations.add(saveReservation(owner, user, store, reservationModel));
            }
        }

        return reservations;
    }

    @Override
    public Reservation saveReservation(User user, ReservationModel newReservation) {
        // Not implemented
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Reservation saveReservation(Owner owner, User user, Store store, ReservationModel newReservation) {
        Reservation reservation = new Reservation();

        reservation.setOwner(owner);
        reservation.setStore(store);
        reservation.setReservationNo(newReservation.getReservationNo());
        reservation.setCashierName(newReservation.getCashierName());
        reservation.setReserverName(newReservation.getReserverName());
        reservation.setFrom(newReservation.getFrom());
        reservation.setTo(newReservation.getTo());
        reservation.setTableName(newReservation.getTableName());
        reservation.setPersons(newReservation.getPersons());


        return reservationRepository.saveAndFlush(reservation);

    }

    @Override
    public Reservation updateReservation(Owner owner, User user, Store store, ReservationModel reservationModel) {
        Reservation reservation = reservationRepository.findByStoreIdAndReservationNo(store.getId(), reservationModel.getReservationNo()).orElseThrow(() -> new RuntimeException("Reservation not found."));

        if (reservationModel.getCashierName() != null) reservation.setCashierName(reservationModel.getCashierName());
        if (reservationModel.getReserverName() != null) reservation.setReserverName(reservationModel.getReserverName());
        if (reservationModel.getFrom() != null) reservation.setFrom(reservationModel.getFrom());
        if (reservationModel.getTo() != null) reservation.setTo(reservationModel.getTo());
        if (reservationModel.getTableName() != null) reservation.setTableName(reservationModel.getTableName());
        if (reservationModel.getPersons() != null) reservation.setPersons(reservationModel.getPersons());


        return reservationRepository.saveAndFlush(reservation);
    }
}
