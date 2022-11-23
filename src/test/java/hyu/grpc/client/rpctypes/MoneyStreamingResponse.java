package hyu.grpc.client.rpctypes;

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
    System.out.println("MoneyStreamingResponse.onCompleted() >> Receive Money Stream Finished");
    latch.countDown();
  }

  @Override
  public void onError(Throwable throwable) {
    System.out.println("MoneyStreamingResponse.onError() >> " + throwable.getMessage());
    latch.countDown();
  }

  @Override
  public void onNext(Money money) {
    System.out.println("MoneyStreamingResponse.onNext() >> Received async money: $" + money.getValue());
  }

}
