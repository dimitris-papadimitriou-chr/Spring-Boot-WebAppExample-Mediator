package data;

import io.vavr.control.Either;
import io.vavr.control.Option;
import model.Client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class MockClientRepository  implements Repository<Client,Integer> {

    private Client[] clients;

    public MockClientRepository() {
        this.clients = new Client[]{
                new Client(1, "jim", 1),
                new Client(2, "john", 3)};
    }

    public CompletableFuture<Either<String,Client>> getById(Integer id) {

        var t = Stream.of(clients)
                .filter(client -> id == client.getId())
                .findAny();

        CompletableFuture<Either<String,Client>> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.complete(Option.ofOptional(t).toEither("no client found"));
            return null;
        });
        return completableFuture;
    }
}
