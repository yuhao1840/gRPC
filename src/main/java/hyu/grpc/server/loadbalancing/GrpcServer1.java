package hyu.grpc.server.loadbalancing;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer1 {

  public static void main(String[] args) {
    try {
      Server server = ServerBuilder.forPort(6565).addService(new BankService()).build();

      server.start();
      System.out.println("GrpcServer1 started........................................");
      server.awaitTermination();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}
