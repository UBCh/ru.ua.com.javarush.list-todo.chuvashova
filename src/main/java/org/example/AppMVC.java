package org.example;

import org.example.domain.Task;
import org.example.service.TaskService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class AppMVC {
    public static void main(String[] args) {
	ConfigurableApplicationContext run = SpringApplication.run(AppMVC.class, args);

	TaskService service = run.getBean(TaskService.class);
	List<Task> all = service.findByAllTask();
	for (Task task : all) {
	    System.out.println(task);
	}

	Task task = service.findByIdTask(3L);
	System.out.println(task);

    }
}