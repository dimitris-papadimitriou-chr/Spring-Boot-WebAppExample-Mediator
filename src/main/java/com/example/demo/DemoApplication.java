package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RequestMapping(value = "/getClientName", method = RequestMethod.GET)
    public ResponseEntity<ClientViewModel> getEmployees2(@RequestParam(value = "clientId", defaultValue = "0") Integer clientId)
    {
        var t  = new ClientViewModel();
        t.setType("rrrr"+clientId);
        t.setValue(1);
        return new ResponseEntity<>(t, HttpStatus.OK);
    }


}