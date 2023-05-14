package cat.dicegame.security.model.Dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private ObjectId id;

    private String name;

    private String email;

    private String password;

    private LocalDateTime localDateTime;

    private String averageSuccessRate;

    //@JsonIgnore
    private Double averageSuccessRateNumber;

    //@JsonIgnore
    private Double averageLoserRateNumber;

    @JsonIgnore
    private List<RollDto> rollsList= new ArrayList<>();

    public PlayerDto(String name){
        this.name=name;
    }

    /*
    public void addRolls(RollDto rollDto){
        rollsList.add(rollDto);
    }
     */

}
