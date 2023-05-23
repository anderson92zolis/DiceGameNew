package cat.dicegame.security.model.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingDto {

    @Schema(description = "OVERAGE SUCCESS RAKING OF ALL THE PLAYER", example = "7.129292")
    private Double overageRankingAllPlayers;

    @JsonIgnore
    @Schema(hidden = true)
    private PlayerDto playerWithTheWorstLossPorcentage;

    @JsonIgnore
    @Schema(hidden = true)
    private PlayerDto playerWithTheWorstSuccessPorcentage;

}
