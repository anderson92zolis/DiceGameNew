package cat.dicegame.security.model.Entity;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Role {
    @Schema(description = "User role")
    USER,
    @Schema(description = "Admin role")
    ADMIN
}
