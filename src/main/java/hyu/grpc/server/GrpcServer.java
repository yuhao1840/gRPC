package hyu.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {

  public static void main(String[] args) {
    try {
      Server server = ServerBuilder.forPort(6565).addService(new BankService()).build();
      server.start();
      server.awaitTermination();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}
