package com.example.Ex18;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightRepo flightRepo;

    public FlightController(FlightRepo flightRepo) {
        this.flightRepo = flightRepo;
    }

    @GetMapping
    public Page<Flight> getAllFlights(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return flightRepo.findAllByOrderByFromAirportAsc(PageRequest.of(page,size));
    }

    @GetMapping("/ontime")
    public List<Flight> getOnTimeFlights() {
        return flightRepo.findByStatus(FlightStatus.ON_TIME);
    }

    @GetMapping("/custom")
    public List<Flight> getCustomFlights(@RequestParam(name = "p1") FlightStatus status1,
                                         @RequestParam(name = "p2") FlightStatus status2) {
        return flightRepo.findByStatusIn(Arrays.asList(status1, status2));
    }

    @PostMapping
    public void createFlights(@RequestParam(required = false, defaultValue = "100") int n) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            Flight flight = new Flight();
            flight.setDescription("Flight " + random.nextInt(100));
            flight.setFromAirport("Airport " + random.nextInt(10));
            flight.setToAirport("Airport " + random.nextInt(10));
            FlightStatus[] statuses = FlightStatus.values();
            flight.setStatus(statuses[random.nextInt(statuses.length)]);
            flightRepo.save(flight);
        }
    }
}