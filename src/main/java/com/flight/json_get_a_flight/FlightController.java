package com.flight.json_get_a_flight;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @GetMapping("/flight")
    public Flight getFlight() {
        Flight flight = new Flight();
        Ticket ticket = new Ticket();
        Person person1 = new Person();

        person1.setFirstName("Ben");
        person1.setLastName("Hunter");

        ticket.setPassenger(person1);
        ticket.setPrice(200);

//        Calendar flightTime = new Calendar();
//        flightTime.set();

        flight.setDeparts(LocalDateTime.of(2017, Month.APRIL, 21, 14, 34));
        flight.setTickets(Arrays.asList(ticket));

        return flight;
    }

    @GetMapping("")
    public List<Flight> getFlights() {
        List<Flight> flightList = new ArrayList<Flight>();

        Flight flight1 = new Flight();
        Ticket ticket1 = new Ticket();
        Person person1 = new Person();

        person1.setFirstName("Ben");
        person1.setLastName("Hunter");

        ticket1.setPassenger(person1);
        ticket1.setPrice(200);

        flight1.setDeparts(LocalDateTime.of(2017, Month.APRIL, 21, 14, 34));
        flight1.setTickets(Arrays.asList(ticket1));

        Flight flight2 = new Flight();
        Ticket ticket2 = new Ticket();
        Person person2 = new Person();

        person2.setFirstName("Ammar");

        ticket2.setPassenger(person2);
        ticket2.setPrice(400);

        flight2.setDeparts(LocalDateTime.of(2019, Month.APRIL, 21, 14, 34));
        flight2.setTickets(Arrays.asList(ticket2));

        flightList.add(flight1);
        flightList.add(flight2);

        return flightList;
    }

    @PostMapping("/tickets/total")
    public Map<String, Integer> postCalculateTicketPrices(@RequestBody TicketList tickets) {
        int totalPrice = 0;
        HashMap<String, Integer> result = new HashMap<>();

        for (Ticket ticket : tickets.getTickets()) {
            totalPrice += ticket.getPrice();
        }
        result.put("result", totalPrice);

        return result;
    }
}