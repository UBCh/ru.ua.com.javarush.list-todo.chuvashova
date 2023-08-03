package org.example.dao;

import org.example.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional(readOnly = true)
public interface TaskDao extends JpaRepository<Task, Long> {


    void delete(Task entity);

    Optional<Task> findById(Long id);


    Task save(Task entity);


    List<Task> findAll();


    long count();
}