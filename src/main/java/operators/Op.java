package operators;

import com.example.demo.SearchViewModel;
import io.vavr.Value;
import io.vavr.control.Either;
import model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;

public class Op {
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


//    public static <L, T, T1> Function<Either<L, T>, CompletableFuture<Either<L, T1>>> bind(Function<T, CompletableFuture<Either<L, T1>>> fn)
//    {
//        var resultViewModel = Match(eitherEmployee).of(
//                Case($Right($()), value -> new ResponseEntity<>(new SearchViewModel(value.getName()), HttpStatus.OK)),
//                Case($Left($()), error -> new ResponseEntity<>(new SearchViewModel(error), HttpStatus.BAD_REQUEST)));
//
//    }


}
