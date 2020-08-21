# Spring Boot WebAppExample Functional
This is a simplistic web application that conforms into a mininal Clean architecture.

Also it uses the vavr.io functional library in order to facilitate a functional style of writing. 
 
 ```java
 
 
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

```
