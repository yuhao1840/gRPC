package hyu.grpc.protobuf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.protobuf.Int32Value;
import hyu.grpc.json.JPerson;
import hyu.grpc.models.Person;

public class PerformanceTest {

  public static void main(String[] args) throws IOException {
    // json
    JPerson person = new JPerson();
    person.setName("sam");
    person.setAge(10);
    ObjectMapper mapper = new ObjectMapper();
    Path path = Paths.get("sam.json");
    Files.write(path, mapper.writeValueAsBytes(person));

    Runnable json = () -> {
      try {
        byte[] bytes = mapper.writeValueAsBytes(person);
        JPerson person1 = mapper.readValue(bytes, JPerson.class);
      }
      catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    };

    // protobuf
    // Person sam = Person.newBuilder().setName("sam").setAge(Int32Value.newBuilder().setValue(25).build()).build();
    Person sam = Person.newBuilder().setName("sam").setAge(25).build();
    path = Paths.get("sam.pb");
    Files.write(path, sam.toByteArray());
    Runnable proto = () -> {
      try {
        byte[] bytes = sam.toByteArray();
        Person sam1 = Person.parseFrom(bytes);
      }
      catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    };

    for (int i = 0; i < 5; i++) {
      runPerformanceTest(json, "JSON");
      runPerformanceTest(proto, "ProtoBuf");
    }
  }

  private static void runPerformanceTest(Runnable runnable, String method) {
    long time1 = System.currentTimeMillis();
    for (int i = 0; i < 1000000; i++) {
      runnable.run();
    }
    long time2 = System.currentTimeMillis();
    System.out.println(method + " : " + (time2 - time1) + " ms");
  }

}
