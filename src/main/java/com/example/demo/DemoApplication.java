package com.example.demo;

import commands.GetClientCommand;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static io.vavr.API.Try;
import static operators.Extensions.throwableMessage;

@SpringBootApplication
@RestController
public class DemoApplication {

     static DemoAppMediator mediator = new DemoAppMediator();

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        mediator.RegisterHandlers();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<SearchViewModel> search(@RequestParam(value = "clientId", defaultValue = "0") Integer clientId) {

        return Try(mediator.send(new GetClientCommand(clientId))::get)
                .getOrElseGet(throwableMessage())
                .fold(
                        error -> new ResponseEntity<>(new SearchViewModel(error), HttpStatus.BAD_REQUEST),
                        value -> new ResponseEntity<>(new SearchViewModel(value.getName()), HttpStatus.OK)
                );
    }

}
