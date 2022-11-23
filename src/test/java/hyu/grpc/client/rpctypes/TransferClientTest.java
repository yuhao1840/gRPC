package hyu.grpc.client.rpctypes;

import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import hyu.grpc.models.TransferRequest;
import hyu.grpc.models.TransferServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.netty.util.internal.ThreadLocalRandom;
import io.grpc.stub.StreamObserver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferClientTest {

  private TransferServiceGrpc.TransferServiceStub nonBlockingStub;

  @BeforeAll
  public void setup() {
    ManagedChannel localhost = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext().build();
    this.nonBlockingStub = TransferServiceGrpc.newStub(localhost);
  }

  @Test
  public void transfer() throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);
    TransferStreamingResponse response = new TransferStreamingResponse(latch);
    StreamObserver<TransferRequest> requestStreamObserver = this.nonBlockingStub.transfer(response);

    for (int i = 0; i < 100; i++) {
      // @formatter:off
      TransferRequest transferRequest = TransferRequest.newBuilder()
          .setFromAccount(ThreadLocalRandom.current().nextInt(1, 11))
          .setToAccount(ThreadLocalRandom.current().nextInt(1, 11))
          .setAmount(ThreadLocalRandom.current().nextInt(1, 21))
          .build();
      // @formatter:on
      requestStreamObserver.onNext(transferRequest);
    }
    requestStreamObserver.onCompleted();
    latch.await();
  }
}
