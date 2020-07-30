package com.example.demo;

import data.MockFutureEitherClientRepository;
import data.MockFutureEitherEmployeeRepository;
import operators.Op;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;

@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<SearchViewModel> search(@RequestParam(value = "clientId", defaultValue = "0") Integer clientId) {
        try {

            var clients = new MockFutureEitherClientRepository();
            var employees = new MockFutureEitherEmployeeRepository();

            var eitherEmployeeFuture =
                    clients.getClientById(clientId)
                            .thenApplyAsync(Op.map(x -> x.getEmployeeId()))
                            .thenComposeAsync(Op.bind(employees::getClientById))
                            .get();

            var result = Match(eitherEmployeeFuture).of(
                    Case($Right($()), value -> value.getName()),
                    Case($Left($()), error -> error));

            var resultViewModel = new SearchViewModel();
            resultViewModel.setResult(result);

            return new ResponseEntity<>(resultViewModel, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }


}