package com.fh.ToDoList;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

class ToDoListApplicationTests {

	@Test
	void shouldBeSpringBootApplication() {
		assertThat(ToDoListApplication.class.getAnnotation(SpringBootApplication.class)).isNotNull();
	}

}
