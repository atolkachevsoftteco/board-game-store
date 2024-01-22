package com.alex.gamestore.jpa;

import com.alex.gamestore.model.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Long> {
}
