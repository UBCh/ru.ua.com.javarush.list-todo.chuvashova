package org.example.service;

import org.example.DAO.TaskDao;
import org.example.domain.Status;
import org.example.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private TaskDao taskDao;


    @Autowired
    public void setTaskDao(TaskDao taskDao) {
	this.taskDao = taskDao;
    }

//
//    public void saveNewTask(TaskDto taskDto) {
//	taskDao.save(taskDto);
//
//    }


    public Task findByIdTask(Long id) {
	return taskDao.findById(id).orElseThrow();
    }


    public void deleteTask(Long id) {
	taskDao.deleteById(id);
    }


    public List<Task> findByAllTask() {
	return sorteListTask(taskDao.getTaskByStatusIsNotNull());
    }


    public Task editDescriptionTask(Long id, String description) {
	taskDao.updateDescriptionById(description, id);
	return taskDao.findById(id).orElseThrow();
    }


    public Task editStatusTask(Long id, Status status) {
	taskDao.updateStatusById(status, id);
	return taskDao.findById(id).orElseThrow();
    }


    private List<Task> sorteListTask(List<Optional<Task>> tasks) {
	List<Task> sorteTask = new ArrayList<>();
	List<Task> sorteTask_done = new ArrayList<>();
	List<Task> sorteTask_isProgress = new ArrayList<>();
	List<Task> sorteTask_pause = new ArrayList<>();
	for (Optional optionalTask : tasks) {
	    Task task = (Task) optionalTask.orElseThrow();
	    switch (task.getStatus()) {
		case DONE -> sorteTask_done.add(task);
		case PAUSED -> sorteTask_pause.add(task);
		case IN_PROGRESS -> sorteTask_isProgress.add(task);
	    }
	    sorteTask.addAll(sorteTask_isProgress);
	    sorteTask.addAll(sorteTask_pause);
	    sorteTask.addAll(sorteTask_done);
	}
	return sorteTask;
    }

}
