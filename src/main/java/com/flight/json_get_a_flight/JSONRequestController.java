package com.flight.json_get_a_flight;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/jr/object-example/")
public class JSONRequestController {

    @PostMapping("/pojo")
    public Person getJsonPerson (@RequestBody Person person){
        return person;
    }

    @PostMapping("/map")
    public Map<String, Object> getJsonObject (@RequestBody HashMap<String, Object> jsonObject){
        return jsonObject;
    }



}
