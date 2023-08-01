package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.domain.Task;
import org.example.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@RestController
@EnableWebMvc
@RequestMapping("/list*")
@RequiredArgsConstructor
public class ListController {

    private final TaskService taskService;


    @GetMapping("/")
    public ModelAndView showAllUsers(ModelAndView modelAndView) {
	List<Task> listOfUsers = taskService.findByAllTask();
	modelAndView.setViewName("list");
	modelAndView.addObject("tasks", listOfUsers.size());
	return modelAndView;
    }

}
