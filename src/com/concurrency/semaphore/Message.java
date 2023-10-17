package com.concurrency.semaphore;

public class Message {
  private int id;
  private int value;

  public Message(int id, int value) {
    this.id = id;
    this.value = value;
  }

  public int getId() {
    return id;
  }

  public int getValue() {
    return value;
  }
}