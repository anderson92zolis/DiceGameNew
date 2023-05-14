package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class RankingDto {

    private Double overageRankingAllPlayer;

    @JsonIgnore
    private PlayerDto playerWithTheWorstLossPorcentage;

    @JsonIgnore
    private PlayerDto playerWithTheWorstSuccessPorcentage;

    public RankingDto(){}


}
