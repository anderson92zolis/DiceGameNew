package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;




@SpringBootApplication
public class TheDiceGameByAzApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(TheDiceGameByAzApplication.class, args);
	}

}
