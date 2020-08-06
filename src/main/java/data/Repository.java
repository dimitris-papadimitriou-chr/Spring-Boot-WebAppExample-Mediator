package data;

import io.vavr.control.Either;

import java.util.concurrent.CompletableFuture;

public interface Repository<T,TKey> {
    CompletableFuture<Either<String, T>> getById(TKey id);
}
