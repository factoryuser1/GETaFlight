package com.flight.json_get_a_flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class Flight {
    @JsonProperty("Departs")
    private LocalDateTime departs;

    @JsonProperty("Tickets")
    private List<Ticket> tickets;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    public LocalDateTime getDeparts() {
        return departs;
    }

    public void setDeparts(LocalDateTime departs) {
        this.departs = departs;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
