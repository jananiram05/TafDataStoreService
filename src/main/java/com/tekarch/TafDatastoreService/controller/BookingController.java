package com.tekarch.TafDatastoreService.controller;
import com.tekarch.TafDatastoreService.model.Booking;
import com.tekarch.TafDatastoreService.repository.BookingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private static final Logger logger = LogManager.getLogger(BookingController.class);
    private final BookingRepository bookingRepository;

    public BookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        logger.info("Creating booking for userId: {} and flightId: {}", booking.getUser().getId(), booking.getFlight().getId());
        booking.setStatus(Booking.BookingStatus.BOOKED);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());
        Booking savedBooking = bookingRepository.save(booking);
        logger.info("Booking created successfully with id: {}", savedBooking.getId());
        return ResponseEntity.ok(savedBooking);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        logger.info("Fetching booking with id: {}", id);
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            logger.info("Booking found with id: {}", id);
            return ResponseEntity.ok(booking.get());
        }
        logger.warn("Booking with id {} not found", id);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable Long userId) {
        logger.info("Fetching bookings for userId: {}", userId);
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        if (!bookings.isEmpty()) {
            logger.info("Bookings found for userId: {}", userId);
            return ResponseEntity.ok(bookings);
        }
        logger.warn("No bookings found for userId: {}", userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        logger.info("Cancelling booking with id: {}", id);
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            booking.setStatus(Booking.BookingStatus.CANCELLED);
            booking.setUpdatedAt(LocalDateTime.now());
            bookingRepository.save(booking);
            logger.info("Booking with id: {} has been cancelled", id);
            return ResponseEntity.ok("Booking cancelled successfully");
        }
        logger.warn("Booking with id: {} not found", id);
        return ResponseEntity.notFound().build();
    }
}

