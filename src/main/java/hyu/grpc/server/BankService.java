package hyu.grpc.server;

import hyu.grpc.models.Balance;
import hyu.grpc.models.BalanceCheckRequest;
import hyu.grpc.models.BankServiceGrpc;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

  @Override
  public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
    System.out.println("BankService.getBalance()..............................");
    int accountNumber = request.getAccountNumber();
    System.out.println("accountNumber=" + accountNumber);
    Balance balance = Balance.newBuilder().setAmount(accountNumber * 10).build();
    System.out.println("balance=" + balance.getAmount());
    responseObserver.onNext(balance);
    responseObserver.onCompleted();
  }

}
