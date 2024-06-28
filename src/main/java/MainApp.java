import classes.*;

public class MainApp {
    public static void main(String[] args) {
        MyThreadPool threadPool = new MyThreadPool(4);

        for (int i = 0; i < 10; i++) {
            final int w = i + 1;

            threadPool.execute(() -> {
                System.out.println(w + " - Start work");

                try {
                    Thread.sleep(1000 + (int) (2000 * Math.random()));
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }

                System.out.println(w + " - End work");
            });
        }

        threadPool.shutdown();
        threadPool.awaitTermination();
        System.out.println("ThreadPool has been terminated");
    }
}
