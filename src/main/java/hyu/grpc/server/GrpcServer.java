package hyu.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {

  public static void main(String[] args) {
    try {
      Server server = ServerBuilder.forPort(6565).addService(new BankService()).addService(new TransferService()).build();

      server.start();
      System.out.println("GrpcServer started........................................");
      server.awaitTermination();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}
