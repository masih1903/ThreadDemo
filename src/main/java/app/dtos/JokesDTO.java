package app.dtos;

import lombok.Data;

@Data
public class JokesDTO
{
    private DadJokeDTO dadJokeDTO;
    private ChuckJokeDTO chuckJokeDTO;

    public String getJokes()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(dadJokeDTO.getJoke() + System.lineSeparator());
        sb.append(chuckJokeDTO.getJoke());
        return sb.toString();
    }
}
