package hyu.grpc.server.loadbalancing;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer2 {

  public static void main(String[] args) {
    try {
      Server server = ServerBuilder.forPort(7575).addService(new BankService()).build();

      server.start();
      System.out.println("GrpcServer2 started........................................");
      server.awaitTermination();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}
