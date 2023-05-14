package cat.dicegame.security.model.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
// @Entity
//@Document(collection= "rolls")
public class Roll {

    private int dice1;


    private int dice2;


    private String result;


    private Date localDateTime;


    /*
    public Roll (Player player){
        this.player= player;
        dice1=(int) (Math.random() * 6) + 1;
        dice2=(int) (Math.random() * 6) + 1;
        result= (dice1 + dice2 == 7) ? "WIN" : "LOST";
    }



    public int  rollDice() {
        return (int) (Math.random() * 6) + 1;
    }

    public String getRollResult() {
        return (dice1 + dice2 == 7) ? "WIN" : "LOST";
    }

    */


}
