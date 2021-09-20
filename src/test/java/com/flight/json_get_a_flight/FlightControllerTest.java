package com.flight.json_get_a_flight;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlightController.class)
public class FlightControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getFlight() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/flights/flight")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Departs", is("2017-04-21 14:34")))
                .andExpect(jsonPath("$.Tickets[0].Passenger.FirstName",is("Ben")))
                .andExpect(jsonPath("$.Tickets[0].Passenger.LastName",is("Hunter")))
                .andExpect(jsonPath("$.Tickets[0].Price",is(200)));

    }

    @Test
    public void getFlights() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/flights")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].Departs", is("2017-04-21 14:34")))
                .andExpect(jsonPath("$[0].Tickets[0].Passenger.FirstName",is("Ben")))
                .andExpect(jsonPath("$[0].Tickets[0].Passenger.LastName",is("Hunter")))
                .andExpect(jsonPath("$[0].Tickets[0].Price",is(200)))
                .andExpect(jsonPath("$[1].Departs", is("2019-04-21 14:34")))
                .andExpect(jsonPath("$[1].Tickets[0].Passenger.FirstName",is("Ammar")))
//                .andExpect(jsonPath("$[1].Tickets[0].Passenger.LastName",is(null)))
                .andExpect(jsonPath("$[1].Tickets[0].Price",is(400)));

    }

}
