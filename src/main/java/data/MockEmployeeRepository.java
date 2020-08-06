package data;

import io.vavr.control.Either;
import io.vavr.control.Option;
import model.Employee;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class MockEmployeeRepository implements Repository<Employee,Integer> {

    private Employee[] clients;

    public MockEmployeeRepository() {
        this.clients = new Employee[]{
                new Employee(1, "jim"),
                new Employee(2, "john")};
    }

    @Override
    public CompletableFuture<Either<String, Employee>> getById(Integer id) {

        var t = Stream.of(clients)
                .filter(client -> id == client.getId())
                .findAny();

        CompletableFuture<Either<String, Employee>> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.complete(Option.ofOptional(t).toEither("no employee found"));
            return null;
        });

        return completableFuture;
    }

}
