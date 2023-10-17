package com.concurrency.reentrant_lock;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageBuffer {
  private final Deque<Message> buffer;
  private final int maxSize;
  private Lock lock = new ReentrantLock();
  private final Condition notEmpty = lock.newCondition();
  private final Condition notFull = lock.newCondition();

  public MessageBuffer(int maxSize) {
    this.maxSize = maxSize;
    this.buffer = new ArrayDeque<>(maxSize);
  }

  public boolean isEmpty() {
    return buffer.size() == 0;
  }

  public boolean isFull() {
    return buffer.size() == maxSize;
  }

  public void offer(Message message) {
    lock.lock();
    try {
      while (isFull()) {
        notFull.await();
      }
      buffer.offer(message);
      
      try {
        Thread.sleep((long) (Math.random() * 100));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      notEmpty.signalAll();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  public Message poll() {
    lock.lock();
    try {
      while (isEmpty()) {
        notEmpty.await();
      }
      Message message = buffer.poll();

      try {
        Thread.sleep((long) (Math.random() * 100));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      notFull.signalAll();
      return message;
    } catch (InterruptedException e) {
      e.printStackTrace();
      return null;
    } finally {
      lock.unlock();
    }
  }
}
