package hyu.grpc.client;

import java.util.concurrent.CountDownLatch;
import hyu.grpc.models.Money;
import io.grpc.stub.StreamObserver;

public class MoneyStreamingResponse implements StreamObserver<Money> {

  private CountDownLatch latch;

  public MoneyStreamingResponse(CountDownLatch latch) {
    this.latch = latch;
  }

  @Override
  public void onCompleted() {
    System.out.println("Receive Money Stream Finished");
    latch.countDown();
  }

  @Override
  public void onError(Throwable throwable) {
    System.out.println(throwable.getMessage());
    latch.countDown();
  }

  @Override
  public void onNext(Money money) {
    System.out.println("Received async money: $" + money.getValue());
  }

}
