package com.fh.ToDoList.web;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI toDoListOpenApi() {
		return new OpenAPI()
				.info(new Info()
						.title("ToDo List API")
						.description("API REST para gerenciamento de tarefas.")
						.version("v1"));
	}
}
