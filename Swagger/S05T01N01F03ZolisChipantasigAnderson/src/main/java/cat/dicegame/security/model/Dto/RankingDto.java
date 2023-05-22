package cat.dicegame.security.model.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingDto {

    private Double overageRankingAllPlayers;

    @JsonIgnore
    private PlayerDto playerWithTheWorstLossPorcentage;

    @JsonIgnore
    private PlayerDto playerWithTheWorstSuccessPorcentage;

}
