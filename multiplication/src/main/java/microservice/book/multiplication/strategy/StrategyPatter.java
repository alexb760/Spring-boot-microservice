package microservice.book.multiplication.strategy;

import java.util.function.BiFunction;

/**
 * @author Alexander Bravo
 */
public class StrategyPatter<T, U, R> {
    private final BiFunction<T, U, R> function;

    public StrategyPatter(BiFunction<T, U, R> function) {
        this.function = function;
    }

    public R doOperation(T A, U B){
        return function.apply(A, B);
    }
}
