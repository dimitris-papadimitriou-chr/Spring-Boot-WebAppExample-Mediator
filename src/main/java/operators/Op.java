package operators;

import io.vavr.control.Either;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;

public class Op {
    public static <L, T, T1> Function<Either<L, T>, Either<L, T1>> map(Function<T, T1> fn)
            throws InterruptedException, ExecutionException {
        return $source -> $source.map(fn);
    }

    public static <L, T, T1> Function<Either<L, T>, CompletableFuture<Either<L, T1>>> bind(Function<T, CompletableFuture<Either<L, T1>>> fn)
            throws InterruptedException, ExecutionException {
        return $source ->  Match($source).of(
                Case($Right($()), value -> fn.apply(value)),
                Case($Left($()), error -> CompletableFuture.completedFuture(Either.left(error))));
    }
}
