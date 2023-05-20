package cat.dicegame.security.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RollDto {

    private int dice1;

    private int dice2;

    private String result;

    private Date localDateTime;
}
