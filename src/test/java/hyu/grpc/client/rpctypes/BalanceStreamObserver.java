package hyu.grpc.client.rpctypes;

import java.util.concurrent.CountDownLatch;
import hyu.grpc.models.Balance;
import io.grpc.stub.StreamObserver;

public class BalanceStreamObserver implements StreamObserver<Balance> {

  private CountDownLatch latch;

  public BalanceStreamObserver(CountDownLatch latch) {
    this.latch = latch;
  }

  @Override
  public void onCompleted() {
    System.out.println("BalanceStreamObserver.onNext() >> Server is done. ");
    this.latch.countDown();
  }

  @Override
  public void onError(Throwable throwable) {
    System.out.println("BalanceStreamObserver.onError() >> " + throwable.getMessage());
    this.latch.countDown();
  }

  @Override
  public void onNext(Balance balance) {
    System.out.println("BalanceStreamObserver.onNext() >> Final Balance : " + balance.getAmount());
  }

}
