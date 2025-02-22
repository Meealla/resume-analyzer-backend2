package webapp.deepseek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class DeepseekServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeepseekServiceApplication.class, args);
	}
}
