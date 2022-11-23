package hyu.grpc.client.rpctypes;

import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import hyu.grpc.models.Balance;
import hyu.grpc.models.BalanceCheckRequest;
import hyu.grpc.models.BankServiceGrpc;
import hyu.grpc.models.DepositRequest;
import hyu.grpc.models.WithdrawRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankClientTest {

  private BankServiceGrpc.BankServiceBlockingStub blockingStub;
  private BankServiceGrpc.BankServiceStub nonBlockingStub;

  @BeforeAll
  public void setup() {
    ManagedChannel localhost = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext().build();
    this.blockingStub = BankServiceGrpc.newBlockingStub(localhost);
    this.nonBlockingStub = BankServiceGrpc.newStub(localhost);
  }

  @Test
  public void balanceTest() {
    int acocuntNumber = 123;
    BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder().setAccountNumber(acocuntNumber).build();
    Balance balance = this.blockingStub.getBalance(balanceCheckRequest);
    System.out.println("Bank Account: " + acocuntNumber + " Balance Amount: " + balance.getAmount());
  }

  @Test
  public void withdrawTest() {
    int acocuntNumber = 123;
    WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder().setAccountNumber(acocuntNumber).setAmount(76).build();
    this.blockingStub.withdraw(withdrawRequest).forEachRemaining(money -> System.out.println("BankClientTest.withdrawTest() >> Received money: " + money.getValue()));
  }

  @Test
  public void withdrawAsyncTest() throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);
    int acocuntNumber = 123;
    WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder().setAccountNumber(acocuntNumber).setAmount(76).build();
    this.nonBlockingStub.withdraw(withdrawRequest, new MoneyStreamingResponse(latch));
    latch.await();
  }

  @Test
  public void cashStreamingRequest() throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);
    StreamObserver<DepositRequest> streamObserver = this.nonBlockingStub.cashDepost(new BalanceStreamObserver(latch));
    for (int i = 0; i < 10; i++) {
      DepositRequest depositRequest = DepositRequest.newBuilder().setAccountNumber(8).setAmount(10).build();
      streamObserver.onNext(depositRequest);
    }
    streamObserver.onCompleted();
    latch.await();
  }

}