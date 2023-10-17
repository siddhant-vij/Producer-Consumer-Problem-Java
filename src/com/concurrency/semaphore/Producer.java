package com.concurrency.semaphore;

import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
  private volatile boolean running;
  private MessageBuffer messageBuffer;
  private AtomicInteger id;

  public Producer(MessageBuffer messageBuffer) {
    this.running = true;
    this.messageBuffer = messageBuffer;
    this.id = new AtomicInteger(1);
  }

  @Override
  public void run() {
    produce();
  }

  private void produce() {
    while (running) {
      Message message = new Message(id.getAndIncrement(), (int) (Math.random() * 100));
      messageBuffer.offer(message);
      System.out.println("Put: " + message.getId());      
    }
  }

  public void stop() {
    running = false;
  }
}
