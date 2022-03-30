package abdrayev.tasktracker.services;

import abdrayev.tasktracker.domain.Task;

import java.util.List;

public interface TaskService {
    List<Task> listAll();

    Task getById(Long id);

    Task save(Task entity);

    void delete(Long id);
}
