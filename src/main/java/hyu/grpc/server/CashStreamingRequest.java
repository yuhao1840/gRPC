package hyu.grpc.server;

import hyu.grpc.models.Balance;
import hyu.grpc.models.DepositRequest;
import io.grpc.stub.StreamObserver;

public class CashStreamingRequest implements StreamObserver<DepositRequest> {

  private StreamObserver<Balance> balanceStreamObserver;
  private int accountBalance;

  public CashStreamingRequest(StreamObserver<Balance> balanceStreamObserver) {
    this.balanceStreamObserver = balanceStreamObserver;
  }

  @Override
  public void onNext(DepositRequest depositRequest) {
    int accountNumber = depositRequest.getAccountNumber();
    int amount = depositRequest.getAmount();
    this.accountBalance = AccountDatabase.addBalance(accountNumber, amount);
  }

  @Override
  public void onCompleted() {
    Balance balance = Balance.newBuilder().setAmount(this.accountBalance).build();
    this.balanceStreamObserver.onNext(balance);
    this.balanceStreamObserver.onCompleted();
  }

  @Override
  public void onError(Throwable throwable) {
    System.out.println("CashStreamingRequest.onError() >> " + throwable.getMessage());
  }

}