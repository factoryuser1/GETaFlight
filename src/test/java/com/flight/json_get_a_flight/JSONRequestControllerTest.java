package com.flight.json_get_a_flight;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JSONRequestController.class)
public class JSONRequestControllerTest {
    //setting and instantiating the ObjectMapper at the test class level to be available to all Tests
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Test
    public void testObjectParams() throws Exception{
        MockHttpServletRequestBuilder request = post("/jr/object-example")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"q\": \"other\", \"from\": \"2020\"}");

        this.mvc.perform(request)
//                .andExpect(status().isOk())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Search: q=something from=2008"));
    }

    @Test
    public void sendJsonInfoWithObjectMapperUsingAHashMap() throws Exception{
        //Option1 using a HasMap to setup the Json string
        HashMap<String, Object> data = new HashMap<>(){
            {
                put("name", "Hercules");
                put("age", 57);
            }
        };

        //Format the Json String using the Mapper
        String jsonString = mapper.writeValueAsString(data);

        MockHttpServletRequestBuilder request = post("/jr/object-example/map")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString);

        this.mvc.perform(request)
//                .andExpect(status().is4xxClientError())
                .andExpect(status().isOk());
    }

    @Test
    public void sendJsonInfoWithObjectMapperUsingAPOJO() throws Exception{

        //Option2 using a POJO to setup the Json string
        Person person1 = new Person();
        person1.setFirstName("Ammar");
        person1.setLastName("Masoud");

        //Format the Json String using the Mapper
        String jsonString = mapper.writeValueAsString(person1);

        MockHttpServletRequestBuilder request = post("/jr/object-example/pojo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString);

        this.mvc.perform(request)
//                .andExpect(status().is4xxClientError())
                .andExpect(status().isOk());
    }

    @Test
    public void getRawJsonBody() throws Exception{
        String jsonFile = getJSON("/data.json");
        MockHttpServletRequestBuilder request = post("/jr/object-example/pojo/file")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFile);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(jsonFile));

    }
    //Helper internal Method
    private String getJSON(String path) throws Exception{

        URL url = this.getClass().getResource(path);
        return new String(Files.readAllBytes(Paths.get(url.getFile())));
    }
}
