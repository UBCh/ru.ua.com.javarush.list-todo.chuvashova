package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.domain.Task;
import org.example.dto.TaskDto;
import org.example.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@EnableWebMvc
@RequestMapping(value = "/list*")

@RequiredArgsConstructor
public class ListController {

    private final TaskService taskService;


    @GetMapping("/")
    public String showAllTasks(Model model,
			       @RequestParam(value = "page", required = false, defaultValue = "1") int page,
			       @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
	List<Task> byAllTask = taskService.findByAllTask((page - 1) * limit, limit);
	model.addAttribute("allTasks", byAllTask);
	model.addAttribute("currentPage", page);
	setPageButton(model, limit);
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


    @PostMapping("/add")
    public String addTask(Model model,
			  @RequestBody TaskDto taskDto) {
	Task task = taskService.craeteTask(taskDto.getDescription());
	model.addAttribute("newTask", task);
	return showAllTasks(model, 1, 10);
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteTask(Model model, @PathVariable Long id) {
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


    private void setPageButton(Model model, int limit) {
	int totalPage = (int) Math.ceil(1.0 * taskService.countAll() / limit);
	if (totalPage > 1) {
	    List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
	    model.addAttribute("pageNumbers", pageNumbers);
	}
    }
}


