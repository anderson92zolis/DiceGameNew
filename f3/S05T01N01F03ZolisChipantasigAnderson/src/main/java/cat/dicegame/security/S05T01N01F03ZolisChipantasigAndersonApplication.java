package cat.dicegame.security;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;


//@SpringBootApplication()
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class S05T01N01F03ZolisChipantasigAndersonApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(S05T01N01F03ZolisChipantasigAndersonApplication.class, args);
	}

}
