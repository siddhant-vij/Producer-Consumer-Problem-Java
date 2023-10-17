package com.concurrency.reentrant_lock;

import java.util.List;

public class Main {
  private static final int MAX_SIZE = 5;

  public static void waitForAllThreadsToFinish(List<Thread> threads) {
    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    MessageBuffer messageBuffer = new MessageBuffer(MAX_SIZE);
    Producer producer = new Producer(messageBuffer);
    Thread producerThread = new Thread(producer, "ProducerThread");
    Consumer consumer = new Consumer(messageBuffer);
    Thread consumerThread = new Thread(consumer, "ConsumerThread");

    List<Thread> threads = List.of(producerThread, consumerThread);

    producerThread.start();
    consumerThread.start();

    Thread.sleep(2000);

    producer.stop();
    consumer.stop();

    waitForAllThreadsToFinish(threads);
  }
}
