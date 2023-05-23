package cat.dicegame.security.model.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RollDto {

    @Schema(description = "First random result of dice game", example = "1")
    private int dice1;

    @Schema(description = "Second random result of dice game", example = "6")
    private int dice2;

    @Schema(description = "Result of the sum of the dice game, 7 WIN other number LOST  ", example = "WIN")
    private String result;

    @Schema(description = "Registration date of the random dice game", example = "2023-05-21T10:11:03.156+00:00")
    private Date localDateTime;

}
