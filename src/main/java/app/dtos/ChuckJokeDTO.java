package app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChuckJokeDTO implements IJoke
{
    private String id;
    private String value;
    private String url;

    @Override
    public String getJoke()
    {
        return "ChuckJoke: " + value;
    }

    @Override
    public void addToJokeDTO(JokesDTO jokesDTO)
    {
        jokesDTO.setChuckJokeDTO(this);
    }
}
