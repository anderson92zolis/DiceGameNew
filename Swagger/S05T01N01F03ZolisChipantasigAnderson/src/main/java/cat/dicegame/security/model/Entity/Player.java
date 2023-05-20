package cat.dicegame.security.model.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Players")
@ApiModel(description = "Details of a Player")
public class Player implements UserDetails {

    @Id
    @ApiModelProperty(notes = "ID unique for a player")
    private ObjectId id;
    @Field("name")
    private String name;
    @Field("email")
    private String email;
    @Field("password")
    private String password;
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

    // SECURITY PART

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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