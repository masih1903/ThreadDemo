package app;

import app.dtos.ChuckJokeDTO;
import app.dtos.DadJokeDTO;
import app.dtos.JokesDTO;
import app.services.CallableFutureExample;
import app.services.JokeService;

public class Main
{
    public static void main(String[] args)
    {
        // Record the start time
        long startTime = System.currentTimeMillis();

        //CallableFutureExample.runTasks();

        JokesDTO jokesDTO = JokeService.getAllJokes();
        System.out.println(jokesDTO.getJokes());

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Task runtime: " + duration + " milliseconds");

    }
}