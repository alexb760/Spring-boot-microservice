package microservice.book.multiplication.strategy;

/**
 * @author Alexander Bravo
 */
public class demo {
  public static void main(String[] args) {
    //
      StrategyPatter<Integer, Integer, Integer> sustrac = new StrategyPatter<>((A, B) -> A - B);
      System.out.println(sustrac.doOperation(9, 5));

      StrategyPatter<String, String, String> concat = new StrategyPatter<>(String::concat);
      System.out.println(concat.doOperation("Hello ", "World"));
  }
}
