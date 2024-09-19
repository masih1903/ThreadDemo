package app.services;

import java.util.List;
import java.util.concurrent.*;

public class CallableFutureExample
{

    public static void runTasks() {
        // Create a fixed thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Create 3 Callable tasks and submit them to the executor
        Callable<String> task1 = () -> {
            TimeUnit.SECONDS.sleep(1); // Sleep for taskId seconds
            return "Task " + 1 + " completed";
        };

        Callable<String> task2 = () -> {
            TimeUnit.SECONDS.sleep(2); // Sleep for taskId seconds
            return "Task " + 2 + " completed";
        };

        Callable<String> task3 = () -> {
            TimeUnit.SECONDS.sleep(3); // Sleep for taskId seconds
            return "Task " + 3 + " completed";
        };

        // Submit the task and add the Future to the list

        List<Future<String>> futures = null;
        try {
            futures = executor.invokeAll(List.of(task1, task2, task3));
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Wait for all tasks to complete and retrieve their results
        for (Future<String> future : futures) {
            try {
                // get() blocks until the result is available
                String result = future.get();
                System.out.println(result);
            }
            catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Shut down the executor service
        executor.shutdown();
    }
}