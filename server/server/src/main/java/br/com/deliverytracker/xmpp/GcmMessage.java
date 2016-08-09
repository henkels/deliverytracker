package br.com.deliverytracker.xmpp;

public class GcmMessage {

  private String gcmId;

  private String number;

  private int deviceId;

  private String message;

  private boolean receipt;

  private boolean notification;

  private boolean redphone;

  private boolean call;

  public GcmMessage() {}


  public GcmMessage(String gcmId, String number, int deviceId, String message,
                    boolean receipt, boolean notification, boolean redphone, boolean call) {
    this.gcmId        = gcmId;
    this.number       = number;
    this.deviceId     = deviceId;
    this.message      = message;
    this.receipt      = receipt;
    this.notification = notification;
    this.redphone     = redphone;
    this.call         = call;
  }

  public String getGcmId() {
    return gcmId;
  }

  public String getNumber() {
    return number;
  }

  public String getMessage() {
    return message;
  }

  public boolean isReceipt() {
    return receipt;
  }

  public boolean isNotification() {
    return notification;
  }

  public boolean isRedphone() {
    return redphone;
  }

  public boolean isCall() {
    return call;
  }

  public int getDeviceId() {
    return deviceId;
  }
}