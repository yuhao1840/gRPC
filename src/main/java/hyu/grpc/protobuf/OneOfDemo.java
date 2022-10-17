package hyu.grpc.protobuf;

import hyu.grpc.models.Credentials;
import hyu.grpc.models.EmailCredentials;
import hyu.grpc.models.PhoneOTP;

public class OneOfDemo {

  public static void main(String[] args) {
    EmailCredentials emailCredentials = EmailCredentials.newBuilder().setEmail("yuhao1840@yahoo.com").setPassword("password").build();
    PhoneOTP phoneOTP = PhoneOTP.newBuilder().setNumber(2024525687).setCode(124556).build();
    Credentials credentials = Credentials.newBuilder().setEmailMode(emailCredentials).setPhoneMode(phoneOTP).build();
    OneOfDemo.login(credentials);
  }

  private static void login(Credentials credentials) {
    switch (credentials.getModeCase()) {
      case EMAILMODE:
        System.out.println(credentials.getEmailMode());
      case PHONEMODE:
        System.out.println(credentials.getPhoneMode());
      default:
        break;
    }
  }

}
