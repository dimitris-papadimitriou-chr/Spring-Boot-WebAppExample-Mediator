package commands;

import an.awesome.pipelinr.Command;
import io.vavr.control.Either;
import model.Employee;

import java.util.concurrent.CompletableFuture;

public class GetClientCommand implements Command<CompletableFuture<Either<String, Employee>>> {

    public Integer getClientId() {
        return clientId;
    }

    private Integer clientId;

    public GetClientCommand(Integer clientId) {
        this.clientId = clientId;
    }


}


