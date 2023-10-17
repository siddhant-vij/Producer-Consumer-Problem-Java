package com.concurrency.reentrant_lock;

public class Consumer implements Runnable {
  private volatile boolean running;
  private MessageBuffer messageBuffer;

  public Consumer(MessageBuffer messageBuffer) {
    this.running = true;
    this.messageBuffer = messageBuffer;
  }

  @Override
  public void run() {
    consume();
  }

  private void consume() {
    while (running) {
      Message message = messageBuffer.poll();
      System.out.println("Got: " + message.getId());
    }
  }

  public void stop() {
    running = false;
  }
}
