package cat.dicegame.security.model.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@Builder  // build a object in a easy way using the DESIGN PATTER BUILDER
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Players")
public class Player implements UserDetails {

    @Id
    @Schema(description = "ID UNIQUE FOR A PLAYER", example = "64673962a24a8e7535f3c442")
    private ObjectId id;


    @Field("name")
    @Schema(description = "NAME OF THE PLAYER", example = "Anderson")
    private String name;

    @Email
    @NotBlank
    @Field("email")
    @Schema(description = "EMAIL OF THE PLAYER", example = "ander@gmail.com")
    private String email;

    @NotBlank
    @Field("password")
    @Schema(description = "PASSWORD OF THE PLAYER", example = "password")
    private String password;


    @Field("localDateTime")
    @Schema(description = "REGISTRATION DATE OF THE PLAYER", example = "2023-05-21T10:11:03.156+00:00")
    private LocalDateTime localDateTime;

    @Field("rollsList")
    @Schema(description = "Player's roll")
    private List<Roll> rollsList;
    @Field(name = "role")
    @Schema(description = "ROLE PLAYER", example = "USER")
    @Enumerated(EnumType.STRING)
    private Role role;

    //CONSTRUCTOR
    public Player(String name) {
        this.name = name;
        this.localDateTime = LocalDateTime.now();
        this.rollsList = new ArrayList<>();
    }

    public void addRolls(Roll roll) {
        rollsList.add(roll);
    }

    // SECURITY PART

    public void deleteRolls() {
        rollsList.clear();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
        /* Granted Authority okay so the object that I want  to return is called Simple granted Authority and
        31:44 here I want to return the role.name okay()  so the role is referencing this role right here
         */
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

/* HERE THE LINK TO DO THE PROJECT

lombok:        https://www.javaguides.net/2021/02/spring-boot-dto-example-entity-to-dto.html


 */