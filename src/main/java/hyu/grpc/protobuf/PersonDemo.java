package hyu.grpc.protobuf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.google.protobuf.Int32Value;
import hyu.grpc.models.Person;

public class PersonDemo {

  public static void main(String[] args) throws IOException {
    Person sam = Person.newBuilder().setName("sam").setAge(Int32Value.newBuilder().setValue(25).build()).build();

    Path path = Paths.get("sam.ser");
    Files.write(path, sam.toByteArray());

    byte[] bytes = Files.readAllBytes(path);
    Person newSam = Person.parseFrom(bytes);
    System.out.println("newSam=>\n" + newSam);
  }

}
