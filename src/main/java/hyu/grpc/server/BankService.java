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
    int amount = AccountDatabase.getBalance(accountNumber);
    System.out.println("amount=" + amount);
    Balance balance = Balance.newBuilder().setAmount(amount).build();
    System.out.println("balance=" + balance.getAmount());
    responseObserver.onNext(balance);
    responseObserver.onCompleted();
  }

}
