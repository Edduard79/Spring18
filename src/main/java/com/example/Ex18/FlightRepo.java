package com.example.Ex18;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepo extends JpaRepository<Flight, Long> {
    Page<Flight> findAllByOrderByFromAirportAsc(PageRequest pageable);

    List<Flight> findByStatus(FlightStatus status);

    List<Flight> findByStatusIn(List<FlightStatus> statuses);
}
