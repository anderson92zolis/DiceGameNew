package cat.dicegame.security.auth;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Schema(description = "Name",example ="andy")
    private String name;

    @NotBlank
    @Schema(description = "Email address", example = "example@example.com")
    private String email;

    @NotBlank
    @Schema(description = "Password", example = "password123")
    private String password;
}
