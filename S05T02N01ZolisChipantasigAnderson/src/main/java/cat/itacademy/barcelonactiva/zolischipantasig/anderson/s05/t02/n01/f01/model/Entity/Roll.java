package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "rolls")
public class Roll {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "dice1")
    private int dice1;

    @Column(name = "dice2")
    private int dice2;

    @Column(name = "winner/loser")
    private String result;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rollDate")
    private Date localDateTime;

    @PrePersist
    private void onCreate(){
        localDateTime = new Date();
    }

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

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
