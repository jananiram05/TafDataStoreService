package com.tekarch.TafDatastoreService.repository;

import com.tekarch.TafDatastoreService.model.Booking;
import com.tekarch.TafDatastoreService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByUserId(Long userId);
}
