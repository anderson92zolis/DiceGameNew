package cat.dicegame.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@OpenAPIDefinition(info = @Info(title = "DICEGAME API", version = "3.0.0", description = "Documentation RESTful API"))
public class S05T01N01F03ZolisChipantasigAndersonApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(S05T01N01F03ZolisChipantasigAndersonApplication.class, args);
	}

}
