package commands;

import mediator.Command;
import data.MockClientRepository;
import data.MockEmployeeRepository;
import data.Repository;
import io.vavr.control.Either;
import model.Client;
import model.Employee;

import java.util.concurrent.CompletableFuture;

import static operators.Extensions.bind;
import static operators.Extensions.map;

public class GetClientCommandHandler implements Command.Handler<GetClientCommand, CompletableFuture<Either<String, Employee>>> {

    Repository<Client,Integer> clients = new MockClientRepository();
    Repository<Employee,Integer> employees = new MockEmployeeRepository();

    @Override
    public CompletableFuture<Either<String, Employee>> handle(GetClientCommand command) {

        return clients
                .getById(command.getClientId())
                .thenApplyAsync(map(Client::getEmployeeId))
                .thenComposeAsync(bind(employees::getById));
    }
}
