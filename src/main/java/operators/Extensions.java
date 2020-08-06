package operators;

import io.vavr.control.Either;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import static io.vavr.API.*;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;

public class Extensions {

    public static <R> Function<Throwable,Either<String, R>> throwableMessage( ) {
        return x->Either.left(x.getMessage());
    }

    public static <L, T, T1> Function<Either<L, T>, Either<L, T1>> map(Function<T, T1> fn) {
        return $source -> $source.map(fn);
    }

    public static <L, T, T1> Function<Either<L, T>, CompletableFuture<Either<L, T1>>> bind(Function<T, CompletableFuture<Either<L, T1>>> fn)
         {
        return $source ->  Match($source).of(
                Case($Right($()), value -> fn.apply(value)),
                Case($Left($()), error -> CompletableFuture.completedFuture(Either.left(error))));
    }

}
