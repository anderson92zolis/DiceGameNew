package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Players")
public class Player {

    //@Transient
    //public static final String SEQUENCE_NAME = "users_sequence";


    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private ObjectId id;

    @Field("name")
    private String name;
    @Field("localDateTime")
    private LocalDateTime localDateTime;
    @Field("rollsList")
    private List<Roll> rollsList;


    //CONSTRUCTOR
    public Player(String name) {
        this.name = name;
        this.localDateTime = LocalDateTime.now();
        this.rollsList = new ArrayList<>();
    }

    public void addRolls(Roll roll) {
        rollsList.add(roll);
    }

    public void deleteRolls() {
        rollsList.clear();
    }

}







/* HERE THE LINK TO DO THE PROJECT

lombok:        https://www.javaguides.net/2021/02/spring-boot-dto-example-entity-to-dto.html


 */