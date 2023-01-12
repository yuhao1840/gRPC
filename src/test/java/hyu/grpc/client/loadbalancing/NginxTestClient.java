package hyu.grpc.client.loadbalancing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import hyu.grpc.models.Balance;
import hyu.grpc.models.BalanceCheckRequest;
import hyu.grpc.models.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NginxTestClient {

  private BankServiceGrpc.BankServiceBlockingStub blockingStub;

  @BeforeAll
  public void setup() {
    ManagedChannel localhost = ManagedChannelBuilder.forAddress("localhost", 8585).usePlaintext().build(); // port 8585 for Nginx Proxy Server
    System.out.println("Channel is created");
    this.blockingStub = BankServiceGrpc.newBlockingStub(localhost);
    System.out.println("Stubs are created");
  }

  @Test
  public void balanceTest() {
    for (int i = 1; i < 11; i++) {
      int acocuntNumber = i;
      BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder().setAccountNumber(acocuntNumber).build();
      Balance balance = this.blockingStub.getBalance(balanceCheckRequest);
      System.out.println("Bank Account: " + acocuntNumber + " Balance Amount: " + balance.getAmount());
    }
  }
}
