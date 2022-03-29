package abdrayev.tasktracker.services;

import abdrayev.tasktracker.domain.Project;
import abdrayev.tasktracker.domain.Task;
import abdrayev.tasktracker.repositories.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
class TaskServiceImplTest {
    @Mock
    TaskRepository taskRepository;
    TaskService taskService;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        taskService = new TaskServiceImpl(taskRepository);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void getById() {
        Task task = Task.builder().id(1L).name("task1").build();
        when(taskRepository.getById(anyLong())).thenReturn((task));
        Task taskFromService = taskService.getById(1L);
        assertEquals(task.getId(), taskFromService.getId());
    }

    @Test
    void save() {
        Project project = Project.builder().id(2L).name("task").build();
        Task task = Task.builder().id(1L).name("task1").project(project).build();

        when(taskRepository.save(ArgumentMatchers.any(Task.class))).thenReturn(task);

        Task taskFromService = taskService.save(task);
        assertNotNull(taskFromService.getId());
        verify(taskRepository, times(1)).save(ArgumentMatchers.any(Task.class));
    }

    @Test
    void delete() {

        Long id =1L;
        taskService.delete(1L);
        verify(taskRepository,times(1)).deleteById(anyLong());
    }
}