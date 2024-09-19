package app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DadJokeDTO implements IJoke
{
    private String id;
    private String joke;
    private int status;

    @Override
    public String getJoke()
    {
        return "DadJoke: " + joke;
    }

    @Override
    public void addToJokeDTO(JokesDTO jokesDTO)
    {
        jokesDTO.setDadJokeDTO(this);
    }
}
