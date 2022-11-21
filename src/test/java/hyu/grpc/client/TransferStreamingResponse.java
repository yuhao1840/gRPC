package hyu.grpc.client;

import java.util.concurrent.CountDownLatch;
import hyu.grpc.models.TransferResponse;
import io.grpc.stub.StreamObserver;

public class TransferStreamingResponse implements StreamObserver<TransferResponse> {

  private CountDownLatch latch;

  public TransferStreamingResponse(CountDownLatch latch) {
    this.latch = latch;
  }

  @Override
  public void onCompleted() {
    System.out.println("TransferStreamingResponse.onCompleted() >> All transfers done");
    latch.countDown();
  }

  @Override
  public void onError(Throwable throwable) {
    System.out.println("TransferStreamingResponse.onError() >> " + throwable.getMessage());
    latch.countDown();
  }

  @Override
  public void onNext(TransferResponse transferResponse) {
    System.out.println("TransferStreamingResponse.onNext() >> transfer status: " + transferResponse.getStatus());
    transferResponse.getAccountsList().stream().map(account -> account.getAccountNumber() + " : " + account.getAmount()).forEach(System.out::println);
    System.out.println("------------------------------------------------------");
  }

}
