package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Entity;

import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Dto.RollDto;
import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "diceGame")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name",nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'ANONYMOUS'") // @Column(name = "name",unique = true, nullable = false)
    //@Name("name")
    // @Transient
    private String name;


// TO ADD THE DATE AUTOMATICALLY:  https://www.baeldung.com/spring-data-jpa-query-by-dat
// good video to check how to stamp for date https://www.youtube.com/watch?v=a4FELDK19Ak



    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date" )
    private Date localDateTime;



    @PrePersist
    private void onCreate(){
        localDateTime = new Date();
    }


    @OneToMany(mappedBy = "player",fetch = FetchType.EAGER, orphanRemoval = true, targetEntity = Roll.class, cascade = CascadeType.ALL)
    private final List<Roll> rollsList = new ArrayList<>();


    //CONSTRUCTOR
    public Player(String name){
        this.name = name;
    }


    public void addRolls(Roll roll){
        rollsList.add(roll);
    }

    public void deleteRolls(){
        rollsList.clear();
    }

}







/* HERE THE LINK TO DO THE PROJECT


CRUD con Java Spring Boot y MySQL:    https://amoelcodigo.com/crud-java-sprig-mysql/

lombok:        https://www.javaguides.net/2021/02/spring-boot-dto-example-entity-to-dto.html


Spring Boot, Spring Data JPA – Rest CRUD API example:   https://www.bezkoder.com/spring-boot-jpa-crud-rest-api/
 */