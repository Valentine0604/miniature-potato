package org.valentine.miniaturepotato.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.valentine.miniaturepotato.entity.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Retrieves a list of tasks that are not completed.
     *
     * @return a list of incomplete tasks
     */
    List<Task> findByCompletedFalse();
}
