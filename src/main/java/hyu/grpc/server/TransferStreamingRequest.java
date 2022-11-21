package hyu.grpc.server;

import hyu.grpc.models.Account;
import hyu.grpc.models.TransferRequest;
import hyu.grpc.models.TransferResponse;
import hyu.grpc.models.TransferStatus;
import io.grpc.stub.StreamObserver;

public class TransferStreamingRequest implements StreamObserver<TransferRequest> {

  private StreamObserver<TransferResponse> transferStreamObserver;

  public TransferStreamingRequest(StreamObserver<TransferResponse> transferStreamObserver) {
    this.transferStreamObserver = transferStreamObserver;
  }

  @Override
  public void onCompleted() {
    System.out.println("TransferStreamingRequest.onCompleted() >> ");
    AccountDatabase.printAccountDetails();
    this.transferStreamObserver.onCompleted();
  }

  @Override
  public void onError(Throwable throwable) {
    System.out.println("TransferStreamingRequest.onError() >> " + throwable.getMessage());
  }

  @Override
  public void onNext(TransferRequest transferRequest) {
    int fromAccount = transferRequest.getFromAccount();
    int toAccount = transferRequest.getToAccount();
    int amount = transferRequest.getAmount();
    int balance = AccountDatabase.getBalance(fromAccount);
    TransferStatus status = TransferStatus.FAILED;

    if (balance >= amount && fromAccount != toAccount) {
      AccountDatabase.deductBalance(fromAccount, amount);
      AccountDatabase.addBalance(toAccount, amount);
      status = TransferStatus.SUCCESS;
    }

    Account fromAccountInfo = Account.newBuilder().setAccountNumber(fromAccount).setAmount(AccountDatabase.getBalance(fromAccount)).build();
    Account toAccountInfo = Account.newBuilder().setAccountNumber(toAccount).setAmount(AccountDatabase.getBalance(toAccount)).build();
    TransferResponse transferResponse = TransferResponse.newBuilder().setStatus(status).addAccounts(fromAccountInfo).addAccounts(toAccountInfo).build();

    this.transferStreamObserver.onNext(transferResponse);
  }

}
