package hyu.grpc.protobuf;

import hyu.grpc.models.Person;

public class DefaultValueDemo {

  public static void main(String[] args) {
    Person person = Person.newBuilder().build();

    System.out.println("City: " + person.getAddress().getCity());
    System.out.println("Has Address? " + person.hasAddress());
  }
}
