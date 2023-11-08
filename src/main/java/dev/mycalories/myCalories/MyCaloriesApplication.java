package dev.mycalories.myCalories;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyCaloriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyCaloriesApplication.class, args);
		System.out.println("Успешный запуск");
	}

}
