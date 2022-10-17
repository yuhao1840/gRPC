package hyu.grpc.server;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
 * This is account database
 * 1 => 10
 * 2 => 20
 * ...
 * 1000 => 10000
 */
public class AccountDatabase {

  private static final Map<Integer, Integer> MAP = IntStream.rangeClosed(1, 1000).boxed().collect(Collectors.toMap(Function.identity(), v -> v * 10));

  public static Integer getBalance(int accountId) {
    // System.out.println("AccountDatabase.getBalance()..............................accountId=" + accountId);
    if (accountId < 1 || accountId > 1000) {
      // System.out.println("Invalid accountId. Must be in range of 1 to 1000.");
      return 0;
    }
    return MAP.get(accountId);
  }

  public static Integer addBalance(int accountId, int amount) {
    return MAP.computeIfPresent(accountId, (k, v) -> v + amount);
  }

  public static Integer deductBalance(int accountId, int amount) {
    return MAP.computeIfPresent(accountId, (k, v) -> v - amount);
  }

}
