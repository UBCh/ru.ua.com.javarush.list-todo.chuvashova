package org.example.service;

import org.example.dao.TaskDao;
import org.example.domain.Status;
import org.example.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private TaskDao taskDao;


    @Autowired
    public void setTaskDao(TaskDao taskDao) {

	this.taskDao = taskDao;
    }


    public Task findByIdTask(Long id) {
	return taskDao.findById(id).orElseThrow();
    }


    public void deleteTaskbyId(Long id) {
	Optional<Task> byId = taskDao.findById(id);
	if (checkAvailabilityTask(byId)) {
	    taskDao.delete(byId.orElseThrow());
	}

    }


    public List<Task> findByAllTask(int offset, int limit) {
	return taskDao.findAll().subList(offset, limit);
    }


    public Long countAll() {
	return taskDao.count();
    }


    @Transactional
    public Task editTask(Long id, String description, Status status) {
	Optional<Task> byId = taskDao.findById(id);
	if (checkAvailabilityTask(byId)) {
	    Task task = byId.orElseThrow();
	    task.setDescription(description);
	    task.setStatus(status);

	    return taskDao.save(task);
	}
	return null;
    }


    public Task craeteTask(String description) {
	Task task = new Task();
	task.setDescription(description);
	task.setStatus(Status.PAUSED);
	return taskDao.save(task);
    }


    private Boolean checkAvailabilityTask(Optional optional) {
	if (optional != null) {
	    return true;
	}
	throw new RuntimeException("task not found");
    }

}
