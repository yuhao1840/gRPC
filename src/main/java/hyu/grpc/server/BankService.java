package hyu.grpc.server;

import hyu.grpc.models.Balance;
import hyu.grpc.models.BalanceCheckRequest;
import hyu.grpc.models.BankServiceGrpc;
import hyu.grpc.models.Money;
import hyu.grpc.models.WithdrawRequest;
import io.grpc.Status;
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

  // withdraw money in stream of $10
  @Override
  public void withdraow(WithdrawRequest request, StreamObserver<Money> responseObserver) {
    System.out.println("BankService.withdraow()..............................");
    int accountNumber = request.getAccountNumber();
    System.out.println("accountNumber=" + accountNumber);
    int withdrawAmount = request.getAmount();
    System.out.println("withdrawAmount=" + withdrawAmount);

    int currentBalance = AccountDatabase.getBalance(accountNumber);
    System.out.println("currentBalance=" + currentBalance);
    if (currentBalance < withdrawAmount) {
      String errMsg = "Not enough balance. You have only $" + currentBalance + " in your account.";
      System.out.println(errMsg);
      Status status = Status.FAILED_PRECONDITION.withDescription(errMsg);
      responseObserver.onError(status.asException());
      return;
    }

    while (withdrawAmount > 10) {
      currentBalance = AccountDatabase.deductBalance(accountNumber, 10);
      Money money = Money.newBuilder().setValue(10).build();
      responseObserver.onNext(money);
      withdrawAmount -= 10;
      System.out.println("BankService.withdraow() just sent $10 to client; $" + withdrawAmount + " still left to be sent; new balance: " + currentBalance);

      try {
        Thread.sleep(1000);
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    if (withdrawAmount > 0) {
      currentBalance = AccountDatabase.deductBalance(accountNumber, withdrawAmount);
      Money money = Money.newBuilder().setValue(withdrawAmount).build();

      System.out.println("BankService.withdraow() just sent $" + withdrawAmount + " to client; withdraw is finished; new balance: " + currentBalance);
      responseObserver.onNext(money);
    }

    int newBalanceAmount = AccountDatabase.getBalance(accountNumber);
    System.out.println("Final new balance=" + newBalanceAmount);

    responseObserver.onCompleted();
    System.out.println("BankService.withdraow() completed");
  }

}
