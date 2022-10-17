package hyu.grpc.protobuf;

//import com.google.protobuf.Int32Value;
import hyu.grpc.models.Address;
import hyu.grpc.models.Car;
import hyu.grpc.models.Person;

public class CompositionDemo {

  public static void main(String[] args) {
    Address address = Address.newBuilder().setPostbox(123).setStreet("main street").setCity("Atlanta").build();

    Car accord = Car.newBuilder().setMake("Honda").setModel("Accord").setYear(2022).build();
    Car civic = Car.newBuilder().setMake("Honda").setModel("Civic").setYear(2020).build();

    // Person sam = Person.newBuilder().setName("sam").setAge(Int32Value.newBuilder().setValue(25).build()).addCar(accord).addCar(civic).setAddress(address).build();
    Person sam = Person.newBuilder().setName("sam").setAge(25).addCar(accord).addCar(civic).setAddress(address).build();

    System.out.println(sam);
  }

}
