package hyu.grpc.protobuf;

import hyu.grpc.models.BodyStyle;
import hyu.grpc.models.Car;
import hyu.grpc.models.Dealer;

public class MapDemo {

  public static void main(String[] args) {
    Car accord = Car.newBuilder().setMake("Honda").setModel("Accord").setYear(2022).setBodyStyle(BodyStyle.SEDAN).build();
    Car civic = Car.newBuilder().setMake("Honda").setModel("Civic").setYear(2020).setBodyStyle(BodyStyle.COUPE).build();

    Dealer dealer = Dealer.newBuilder().putModel(2022, accord).putModel(2020, civic).build();

    System.out.println(dealer);

    Car car2020 = dealer.getModelOrThrow(2020);
    System.out.println(car2020.getBodyStyle());
  }

}
