package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableMongoRepositories(basePackages = "cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Repository")
public class S05T02N01F2ZolisChipantasigAndersonApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(S05T02N01F2ZolisChipantasigAndersonApplication.class, args);
	}

}
