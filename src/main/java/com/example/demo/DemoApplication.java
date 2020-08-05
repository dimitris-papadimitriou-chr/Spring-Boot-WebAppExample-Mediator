package com.example.demo;

import Commands.GetClientCommand;
import Commands.GetClientCommandHandler;
import Pipelinr.Pipeline;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

import static io.vavr.API.Try;
import static operators.Op.throwableMessage;

@SpringBootApplication
@RestController
public class DemoApplication {
    static Pipeline mediator;
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        mediator = new Pipelinr.Pipelinr()
                .with(
                        () -> Stream.of(new GetClientCommandHandler())
                );
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


/*    public ResponseEntity<SearchViewModel> search1(Integer clientId) {

        var eitherEmployeeFuture = clients
                .getClientById(clientId)
                .thenApplyAsync(map(Client::getEmployeeId))
                .thenComposeAsync(bind(employees::getClientById));

        var eitherEmployee =
                Try(eitherEmployeeFuture::get)
                        .getOrElseGet(x -> Either.left(x.getMessage()));

        var resultViewModel = Match(eitherEmployee).of(
                Case($Right($()), value -> new ResponseEntity<>(new SearchViewModel(value.getName()), HttpStatus.OK)),
                Case($Left($()), error -> new ResponseEntity<>(new SearchViewModel(error), HttpStatus.BAD_REQUEST)));

        return resultViewModel;

    }*/
}
