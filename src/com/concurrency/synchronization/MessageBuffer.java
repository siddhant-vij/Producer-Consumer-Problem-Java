package com.concurrency.synchronization;

import java.util.ArrayDeque;
import java.util.Deque;

public class MessageBuffer {
  private final Deque<Message> buffer;
  private final int maxSize;

  public MessageBuffer(int maxSize) {
    this.maxSize = maxSize;
    this.buffer = new ArrayDeque<>(maxSize);
  }

  public synchronized boolean isEmpty() {
    return buffer.size() == 0;
  }

  public synchronized boolean isFull() {
    return buffer.size() == maxSize;
  }

  public synchronized void offer(Message message) {
    while (isFull()) {
      try {
        wait();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    buffer.offer(message);

    try {
      Thread.sleep((long) (Math.random() * 100));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    notifyAll();
  }

  public synchronized Message poll() {
    while (isEmpty()) {
      try {
        wait();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    Message message = buffer.poll();

    try {
      Thread.sleep((long) (Math.random() * 100));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    notifyAll();
    return message;
  }
}
