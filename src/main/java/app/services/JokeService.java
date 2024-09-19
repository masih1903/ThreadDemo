package app.services;

import app.dtos.ChuckJokeDTO;
import app.dtos.DadJokeDTO;
import app.dtos.IJoke;
import app.dtos.JokesDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.*;

public class JokeService
{

    public static <T> T getFromApi(String uri, Class<T> dtoClass)
    {
        ObjectMapper objectMapper = new ObjectMapper(); // Jackson prep

        try
        {
            // Create an HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            // Create a request
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .header("Accept", "application/json")
                    .uri(new URI(uri))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check the status code and return the appropriate DTO class
            if (response.statusCode() == 200)
            {
                return objectMapper.readValue(response.body(), dtoClass);
            } else
            {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static JokesDTO getAllJokes()
    {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        JokesDTO jokesDTO = new JokesDTO();

        Callable<IJoke> dadJokeTask = () ->
        {
            DadJokeDTO dadJokeDTO = getFromApi("https://icanhazdadjoke.com", DadJokeDTO.class);
            return dadJokeDTO;
        };

        Callable<IJoke> chuckJokeTask = () ->
        {
            ChuckJokeDTO chuckJokeDTO = getFromApi("https://api.chucknorris.io/jokes/random", ChuckJokeDTO.class);
            return chuckJokeDTO;
        };

        List<Future<IJoke>> futures;

        try
        {
            futures = executor.invokeAll(List.of(dadJokeTask, chuckJokeTask));
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);

        }

        for (Future<IJoke> future : futures)
        {
            try
            {
                IJoke joke = future.get();
                joke.addToJokeDTO(jokesDTO);
            } catch (InterruptedException | ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        return jokesDTO;
    }
}


