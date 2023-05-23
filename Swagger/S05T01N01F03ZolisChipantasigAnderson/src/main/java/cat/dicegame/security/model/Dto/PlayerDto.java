package cat.dicegame.security.model.Dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {

    @JsonIgnore
    @Schema(hidden = true)
    private ObjectId id;

    @Schema(description = "NAME OF THE PLAYER", example = "Anderson")
    private String name;

    @JsonIgnore
    @Schema(hidden = true)
    private String email;

    @JsonIgnore
    @Schema(hidden = true)
    private String password;

    @Schema(description = "REGISTRATION DATE OF THE PLAYER", example = "2023-05-21T10:11:03.156+00:00")
    private LocalDateTime localDateTime;

    @Schema(description = "OVERAGE SUCCESSFUL RATE MESSAGE", example = "YOUR AVERAGE SUCCESS RATE IS 10.0%")
    private String averageSuccessRate;

    @Schema(description = "OVERAGE SUCCESSFUL RATE NUMBER", example = "10.0")
    private Double averageSuccessRateNumber;

    @Schema(description = "OVERAGE LOSER RATE", example = "90.0")
    private Double averageLoserRateNumber;

    @JsonIgnore
    @Schema(hidden = true)
    private List<RollDto> rollsList= new ArrayList<>();

    public PlayerDto(String name){
        this.name=name;
    }
}
