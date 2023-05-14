package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
public class PlayerDto {

    @JsonIgnore
    private int id;

    private String name;

    private Date localDateTime;

    private String averageSuccessRate;

    //@JsonIgnore
    private Double averageSuccessRateNumber;

    //@JsonIgnore
    private Double averageLoserRateNumber;

    @JsonIgnore
    private List<RollDto> rollsList = new ArrayList<>();

    /*
    public PlayerDto(String name){
        this.name=name;
    }*/

    /*
    public void addRolls(RollDto rollDto){
        rollsList.add(rollDto);
    }
     */

}
