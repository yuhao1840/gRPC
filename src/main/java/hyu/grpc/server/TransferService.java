package hyu.grpc.server;

import hyu.grpc.models.TransferRequest;
import hyu.grpc.models.TransferResponse;
import hyu.grpc.models.TransferServiceGrpc;
import io.grpc.stub.StreamObserver;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {

  @Override
  public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
    return new TransferStreamingRequest(responseObserver);
  }

}
