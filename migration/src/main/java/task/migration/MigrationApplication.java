package task.migration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MigrationApplication {

	public static void main(String[] args) {
		var applicationContext = SpringApplication.run(MigrationApplication.class, args);
		applicationContext.close();
	}

}
