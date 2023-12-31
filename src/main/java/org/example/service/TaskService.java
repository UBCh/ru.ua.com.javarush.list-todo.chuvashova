package org.example.service;

import org.example.dao.TaskDao;
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


    @Transactional
    public void deleteTaskbyId(Long id) {
	Optional<Task> byId = taskDao.findById(id);
	if (checkAvailabilityTask(byId)) {
	    taskDao.delete(byId.orElseThrow());
	}

    }


    public List<Task> findByAllTask(int offset, int limit) {
	List<Task> all = taskDao.findAll();
	if ((offset + limit) > all.size()) {
	    var temp = all.size() % 10;
	    temp = limit - temp;
	    limit = limit - temp;
	}

	return all.subList(offset, offset + limit);
    }


    public Long countAll() {
	return taskDao.count();
    }


    @Transactional
    public void editTask(Task task) {

	taskDao.save(task);
    }


    @Transactional
    public Task craeteTask(Task task) {
	return taskDao.save(task);
    }


    private Boolean checkAvailabilityTask(Optional optional) {
	if (optional != null) {
	    return true;
	}
	throw new RuntimeException("task not found");
    }

}
