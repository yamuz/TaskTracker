package abdrayev.tasktracker.bootstrap;

import abdrayev.tasktracker.domain.Project;
import abdrayev.tasktracker.domain.ProjectStatus;
import abdrayev.tasktracker.domain.Task;
import abdrayev.tasktracker.domain.TaskStatus;
import abdrayev.tasktracker.repositories.ProjectRepository;
import abdrayev.tasktracker.repositories.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Bootstrap data (projects and tasks)
 */
@Component
public class CommandLineFiller implements CommandLineRunner {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public CommandLineFiller(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args)  {
        if (projectRepository.findAll().isEmpty())
            createProjectsAndTasks();
    }

    private void createProjectsAndTasks(){

        Project project = Project.builder()
                .id(3L)
                .name("Project tracker app")
                .startDate(Date.valueOf(LocalDate.now()))
                .comletionDate(Date.valueOf(LocalDate.of(2022,3,30)))
                .priority(1)
                .status(ProjectStatus.NotStarted)
                .build();
        projectRepository.save(project);
        Task task = Task.builder()
                .id(1L)
                .name("make mvp")
                .priority(1)
                .description("make working app deplyed on public hosting")
                .status(TaskStatus.ToDo)
                .priority(1)
                .project(project)
                .build();
        taskRepository.save(task);

        Project project2 = Project.builder()
                .id(4L)
                .name("Taxi app")
                .startDate(Date.valueOf(LocalDate.now()))
                .comletionDate(Date.valueOf(LocalDate.of(2023,3,30)))
                .priority(2)
                .status(ProjectStatus.NotStarted)
                .build();
        projectRepository.save(project2);
        Task task2 = Task.builder()
                .id(2L)
                .name("make mvp")
                .priority(1)
                .description("make working app deplyed on public hosting")
                .status(TaskStatus.ToDo)
                .priority(1)
                .project(project2)
                .build();
        taskRepository.save(task);
    }
}
