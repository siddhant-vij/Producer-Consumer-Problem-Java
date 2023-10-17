package com.concurrency.blocking_queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MessageBuffer {
  private final BlockingQueue<Message> buffer;
  private final int maxSize;

  public MessageBuffer(int maxSize) {
    this.maxSize = maxSize;
    this.buffer = new ArrayBlockingQueue<>(maxSize);
  }

  public boolean isEmpty() {
    return buffer.size() == 0;
  }

  public boolean isFull() {
    return buffer.size() == maxSize;
  }

  public void offer(Message message) {
    try {
      buffer.put(message);
      Thread.sleep((long) (Math.random() * 100));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public Message poll() {
    try {
      Message message = buffer.take();
      Thread.sleep((long) (Math.random() * 100));
      return message;
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return null;
  }
}
