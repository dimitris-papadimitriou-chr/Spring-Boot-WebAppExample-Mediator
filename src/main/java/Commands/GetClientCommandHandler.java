package Commands;

import Pipelinr.Command;
import data.MockFutureEitherClientRepository;
import data.MockFutureEitherEmployeeRepository;
import io.vavr.control.Either;
import model.Client;
import model.Employee;

import java.util.concurrent.CompletableFuture;

import static operators.Op.bind;
import static operators.Op.map;

public class GetClientCommandHandler implements Command.Handler<GetClientCommand, CompletableFuture<Either<String, Employee>>> {

    MockFutureEitherClientRepository clients = new MockFutureEitherClientRepository();
    MockFutureEitherEmployeeRepository employees = new MockFutureEitherEmployeeRepository();

    @Override
    public CompletableFuture<Either<String, Employee>> handle(GetClientCommand command) {

        return clients
                .getClientById(command.getClientId())
                .thenApplyAsync(map(Client::getEmployeeId))
                .thenComposeAsync(bind(employees::getClientById));
    }
}
