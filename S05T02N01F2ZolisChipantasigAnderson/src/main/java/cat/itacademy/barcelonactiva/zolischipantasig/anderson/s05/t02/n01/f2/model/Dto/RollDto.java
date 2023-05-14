package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Dto;

import lombok.Data;

import java.util.Date;


@Data
public class RollDto {

    private int dice1;

    private int dice2;

    private String result;

    private Date localDateTime;

    public RollDto(){}

    /*

    public RollDto(PlayerDto playerDto){
        this.playerDto=playerDto;
        dice1=rollDice();
        dice2=rollDice();
        result= getRollResult();
    }

    */


    /*
    public int  rollDice() {
        return (int) (Math.random() * 6) + 1;
    }

    public String getRollResult() {
        return (dice1 + dice2 == 7) ? "WIN" : "LOST";
    }


     */


}
