package com.tekarch.TafDatastoreService.controller;


import com.tekarch.TafDatastoreService.model.Flight;
import com.tekarch.TafDatastoreService.repository.FlightRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightRepository flightRepository;

    public FlightController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @GetMapping
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        return flightRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Flight addFlight(@RequestBody Flight flight) {
        flight.setCreatedAt(LocalDateTime.now());
        return flightRepository.save(flight);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        return flightRepository.findById(id)
                .map(existingFlight -> {
                    existingFlight.setFlightNumber(flight.getFlightNumber());
                    existingFlight.setDeparture(flight.getDeparture());
                    existingFlight.setArrival(flight.getArrival());
                    existingFlight.setDepartureTime(flight.getDepartureTime());
                    existingFlight.setArrivalTime(flight.getArrivalTime());
                    existingFlight.setPrice(flight.getPrice());
                    existingFlight.setAvailableSeats(flight.getAvailableSeats());
                    existingFlight.setUpdatedAt(LocalDateTime.now());
                    return ResponseEntity.ok(flightRepository.save(existingFlight));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        return flightRepository.findById(id)
                .map(existingFlight -> {
                    flightRepository.delete(existingFlight);
                    return ResponseEntity.noContent().<Void>build(); // Explicitly specify <Void> here
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
