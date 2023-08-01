package org.example.DAO;

import org.example.domain.Status;
import org.example.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional(readOnly = true)
public interface TaskDao extends JpaRepository<Task, Long> {


    @Transactional
    @Modifying
    @Query("update Task t set t.status = ?1 where t.id = ?2")
    int updateStatusById(Status status, Long id);

    @Transactional
    @Modifying
    @Query("update Task t set t.description = ?1 where t.id = ?2")
    int updateDescriptionById(String description, Long id);


    @Query("select t from Task t where t.status is not null")
    List<Optional<Task>> getTaskByStatusIsNotNull();

    void deleteById(Long Id);

    Optional<Task> findById(Long id);



}
