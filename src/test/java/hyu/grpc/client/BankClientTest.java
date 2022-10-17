package hyu.grpc.client;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import hyu.grpc.models.Balance;
import hyu.grpc.models.BalanceCheckRequest;
import hyu.grpc.models.BankServiceGrpc;
import hyu.grpc.models.BankServiceGrpc.BankServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankClientTest {

  private BankServiceBlockingStub bankServiceBlockingStub;

  @BeforeAll
  public void setup() {
    ManagedChannel localhost = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext().build();
    this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(localhost);
  }

  @Test
  public void balanceTest() {
    int acocuntNumber = 123;
    BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder().setAccountNumber(acocuntNumber).build();
    Balance balance = this.bankServiceBlockingStub.getBalance(balanceCheckRequest);
    System.out.println("Bank Account: " + acocuntNumber + " Balance Amount: " + balance.getAmount());
    assertTrue(acocuntNumber * 10 == balance.getAmount());
  }
}
