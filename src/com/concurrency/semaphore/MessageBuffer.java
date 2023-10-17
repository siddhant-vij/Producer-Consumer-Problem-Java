package com.concurrency.semaphore;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Semaphore;

public class MessageBuffer {
  private final Deque<Message> buffer;
  private final int maxSize;
  private Semaphore emptySemaphore;
  private Semaphore fullSemaphore;

  public MessageBuffer(int maxSize) {
    this.maxSize = maxSize;
    this.buffer = new ArrayDeque<>(maxSize);
    this.emptySemaphore = new Semaphore(maxSize);
    this.fullSemaphore = new Semaphore(0);
  }

  public boolean isEmpty() {
    return buffer.size() == 0;
  }

  public boolean isFull() {
    return buffer.size() == maxSize;
  }

  public void offer(Message message) {
    try {
      emptySemaphore.acquire();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    synchronized (buffer) {
      buffer.offer(message);
    }

    try {
      Thread.sleep((long) (Math.random() * 100));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    fullSemaphore.release();
  }

  public Message poll() {
    try {
      fullSemaphore.acquire();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Message message;
    synchronized (buffer) {
      message = buffer.poll();
    }

    try {
      Thread.sleep((long) (Math.random() * 100));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    emptySemaphore.release();
    return message;
  }
}
