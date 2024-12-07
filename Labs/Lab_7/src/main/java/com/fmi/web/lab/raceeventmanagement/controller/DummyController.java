package com.fmi.web.lab.raceeventmanagement.controller;

import com.fmi.web.lab.raceeventmanagement.model.Racer;
import com.fmi.web.lab.raceeventmanagement.model.Team;
import com.fmi.web.lab.raceeventmanagement.model.Track;
import com.fmi.web.lab.raceeventmanagement.repository.sequence.RaceSequence;
import com.fmi.web.lab.raceeventmanagement.repository.sequence.TeamSequence;
import com.fmi.web.lab.raceeventmanagement.service.EventService;
import com.fmi.web.lab.raceeventmanagement.service.RacerService;
import com.fmi.web.lab.raceeventmanagement.service.TeamService;
import com.fmi.web.lab.raceeventmanagement.service.TrackService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/* Some theory
   @PathVariable -> .../{id} (values that goes after PATH part of the URL specified in the class name by @RequestMapping(...))
   @RequestParam -> ...?{id}
*/

//@RestController
//@RequestMapping("/cars")
/*@Controller*/
public class DummyController {
    @Autowired
    EventService eventService;

    // GetMapping: no path variables, no query parameters
    /*
    @GetMapping("/car") // -> final path to search for: "/cars/car"
    public String getCar() {
        return "ferrari";
        // URL: localhost:8080/cars/car // "8080" default port
    }
    */

    // GetMapping: no path variables with query parameters + one parameter is Optional
    /*
    @GetMapping
    public String getCar(@RequestParam(name = "color") String color, @RequestParam(required = false, name = "code") Integer code) {
        if(color.equals("red")) {
            return "ferrari with code " + code;
        } else {
            return "opel with code " + code;
        }
        // URL: localhost:8080/cars?color=r
    }
    */

    // GetMapping: different @PathVariable name in @GetMapping and parameter name
    // hint: if @GetMapping("{cid}") is not equal to "id" as parameter's name, then specify explicitly
    // name of path variable with @PathVariable("cid")
    /*
    @GetMapping("{cid}")
    public String getCar(@PathVariable("cid") Integer id) { // @PathVariable -> value after last "/" is a variable
        if(id == 1) {
            return "ferrari";
        } else {
            return "opel";
        }
        // localhost:8080/cars/2
    }
    */

    // GetMapping: multiple @PathVariable declarations
    // !!! You need to specify all @PathVariable's also in @GetMapping clause before function.
    // Otherwise it'll not work
    /*
    @GetMapping("/legends/{id}/{brand}")
    public String getCar(@PathVariable("id") Integer id, @PathVariable("brand") String brand) {
        if(brand != null && brand.equals("Ford")) {
            return String.format("Brand: %s, model: mustang, modification: Shelby gt500, id: %d", brand, id);
        } else if(brand != null && brand.equals("Nissan")) {
            return String.format("Brand: %s, model: Gtr, modification: Nismo, id: %d", brand, id);
        } else {
            return "Another simple car";
        }
        // localhost:8080/cars/2
    }
    */
}
