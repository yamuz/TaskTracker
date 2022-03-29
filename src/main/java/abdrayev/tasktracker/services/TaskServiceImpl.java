package abdrayev.tasktracker.services;

import abdrayev.tasktracker.domain.Task;
import abdrayev.tasktracker.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> listAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task getById(Long id) {
        return taskRepository.getById(id);
    }

    @Transactional
    @Override
    public Task save(Task task) {
        if (task.getProject() == null)
            throw new IllegalArgumentException("project is missing for task:" + task.getName());
        return taskRepository.save(task);
    }

    @Transactional
    @Override
    public void delete(Long id) {
       taskRepository.deleteById(id);
    }
}
