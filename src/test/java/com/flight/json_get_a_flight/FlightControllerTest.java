package com.flight.json_get_a_flight;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FlightController.class)
public class FlightControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getFlightForOnePerson() throws Exception {
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
    public void getFlightsReturnMultipleFlightsInJson() throws Exception {
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
//                .andExpect(jsonPath("$[1].Tickets[0].Passenger.LastName",is("Masoud")))
                .andExpect(jsonPath("$[1].Tickets[0].Price",is(400)));
    }

    @Test
    public void postJsonToFLightsTicketCalculatesSumOfTicketPricesWithStringLiteral() throws Exception{
        MockHttpServletRequestBuilder request = post("/flights/tickets/total")
                .contentType(MediaType.APPLICATION_JSON)
                .content("  {\n" +
                        "    \"tickets\": [\n" +
                        "      {\n" +
                        "        \"Passenger\": {\n" +
                        "          \"FirstName\": \"Some name\",\n" +
                        "          \"LastName\": \"Some other name\"\n" +
                        "        },\n" +
                        "        \"Price\": 200\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"Passenger\": {\n" +
                        "          \"FirstName\": \"Name B\",\n" +
                        "          \"LastName\": \"Name C\"\n" +
                        "        },\n" +
                        "        \"Price\": 150\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }");
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("{" +
                        "\"result\":350" +
                        "}"));

    }

    @Test
    public void postJsonToFlightsTicketCalculatesSumOfTicketPricesWithObjectMapper() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        TicketList ticketList = new TicketList();

        ArrayList<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket(new Person("Ben", "Hunter"), 200));
        tickets.add(new Ticket(new Person("Ammar", "Masoud"), 150));

        ticketList.setTickets(tickets);

        String json = objectMapper.writeValueAsString(ticketList);

        RequestBuilder request = MockMvcRequestBuilders.post("/flights/tickets/total")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("{" +
                        "\"result\":350" +
                        "}"));
    }
    @Test
    public void postJsonToFlightsTicketCalculatesSumOfTicketPricesWithFileReader() throws Exception{
        String jsonFile = getJSON("/ben.json");
        MockHttpServletRequestBuilder request = post("/flights/tickets/total")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFile);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("{" +
                        "\"result\":350" +
                        "}"));
    }


    //Helper internal Method
    private String getJSON(String path) throws Exception{

        URL url = this.getClass().getResource(path);
        return new String(Files.readAllBytes(Paths.get(url.getFile())));
    }
}
