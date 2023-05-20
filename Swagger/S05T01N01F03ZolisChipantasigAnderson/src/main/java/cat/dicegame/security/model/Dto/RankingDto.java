package cat.dicegame.security.model.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingDto {

    private Double overageRankingAllPlayer;

    @JsonIgnore
    private PlayerDto playerWithTheWorstLossPorcentage;

    @JsonIgnore
    private PlayerDto playerWithTheWorstSuccessPorcentage;

}
