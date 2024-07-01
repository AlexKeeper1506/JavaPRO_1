package classes;

import java.util.LinkedList;

public class MyThread extends Thread {
    private final LinkedList<Runnable> workQueue;
    private volatile boolean isStopped;

    public MyThread(LinkedList<Runnable> workQueue) {
        this.workQueue = workQueue;
    }

    @Override
    public void run() {
        while (true) {
            Runnable command = null;

            synchronized (workQueue) {
                if (isStopped && workQueue.isEmpty())
                    break;

                if (workQueue.isEmpty()) {
                    try {
                        workQueue.wait();
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }

                if (!workQueue.isEmpty()) {
                    command = workQueue.poll();
                }
            }

            if (command != null) {
                command.run();
            }
        }
    }

    public void stopThread() {
        isStopped = true;
    }
}
