package classes;

import java.util.LinkedList;

public class MyThreadPool {
    private final int poolSize;
    private final LinkedList<Runnable> workQueue;
    private final MyThread[] threads;
    private boolean isShutdown;

    public MyThreadPool(int poolSize) {
        this.poolSize = poolSize;
        workQueue = new LinkedList<>();
        threads = new MyThread[poolSize];

        for (int i = 0; i < poolSize; i++) {
            threads[i] = new MyThread(workQueue);
            threads[i].start();
        }
    }

    public void execute(Runnable command) {
        if (isShutdown)
            throw new IllegalStateException("ThreadPool has been shutdown");

        if (command == null)
            throw new NullPointerException("Command cannot be null");

        synchronized (workQueue) {
            workQueue.offer(command);
            workQueue.notifyAll();
        }
    }

    public void shutdown() {
        isShutdown = true;
        for (MyThread thread : threads) {
            thread.stopThread();
        }
    }

    public void awaitTermination() {
        boolean working;

        while(true) {
            working = false;

            for (MyThread thread : threads) {
                if (thread.isAlive()) {
                    working = true;
                    break;
                }
            }

            if (!working)
                break;
        }
    }
}
