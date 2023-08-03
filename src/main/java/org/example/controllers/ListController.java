package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.domain.Task;
import org.example.dto.TaskDto;
import org.example.service.TaskService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@RestController
@EnableWebMvc
@RequestMapping(value = "/list*")

@RequiredArgsConstructor
public class ListController {

    private final TaskService taskService;


    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String showAllTasks(Model model,
			       @RequestParam(value = "page", required = false, defaultValue = "1") int page,
			       @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
	List<Task> byAllTask = taskService.findByAllTask((page - 1) * limit, limit);
	model.addAttribute("allTasks", byAllTask);

	return "index";
    }


    @PostMapping("/{id}")
    public void redactorTask(Model model,
			     @PathVariable Long id,
			     @RequestBody TaskDto taskDto) {
	if (checkIdTask(id)) {
	    taskService.editTask(id, taskDto.getDescription(), taskDto.getStatus());
	}

    }


    @PostMapping("/")
    public String addTask(Model model,
			  @RequestBody TaskDto taskDto) {
	taskService.craeteTask(taskDto.getDescription(), taskDto.getStatus());
	return showAllTasks(model, 1, 10);
    }


    @DeleteMapping("/{id}")
    public String deleteTask(Model model,
			     @PathVariable Long id) {
	if (checkIdTask(id)) {
	    taskService.deleteTaskbyId(id);
	}
	return showAllTasks(model, 1, 10);
    }


    private Boolean checkIdTask(Long id) {
	if (id != null || id > 0) {
	    return true;
	}
	throw new RuntimeException("id invalide");
    }

}


