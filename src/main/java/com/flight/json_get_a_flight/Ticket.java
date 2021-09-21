package com.flight.json_get_a_flight;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Ticket {
    @JsonProperty("Passenger")
    private Person passenger;

    @JsonProperty("Price")
    private int price;

    public Ticket() {}

    public Ticket(Person passenger, int price) {
        this.passenger = passenger;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }


    public void setPrice(int price) {
        this.price = price;
    }

    public Person getPassenger() {
        return passenger;
    }

    public void setPassenger(Person passenger) {
        this.passenger = passenger;
    }
}
