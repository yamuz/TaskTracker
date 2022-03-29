package abdrayev.tasktracker.services;

import abdrayev.tasktracker.domain.Project;
import abdrayev.tasktracker.domain.Task;
import abdrayev.tasktracker.repositories.ProjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProjectServiceImplTest {
    ProjectService projectService;
    @Mock
    ProjectRepository projectRepository;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        projectService = new ProjectServiceImpl(projectRepository);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    void getFilteredProjects() {
        Project project1 = Project.builder().id(1L).name("Proj1").build();
        Project project2 = Project.builder().id(2L).name("Proj2").build();
        List<Project> projectList = new ArrayList<>(Arrays.asList(project1, project2));

        when(projectRepository.findProjectsByCustomFields(anyMap(), anyMap())).thenReturn(projectList);

        Map<String ,Object> mapFilter = new HashMap<>();
        mapFilter.put("name", "Proj1");
        Map<String ,Object> mapSort = new HashMap<>();

        List<Project> projectList2 = projectService.getFilteredProjects(mapFilter, mapSort);
        assertEquals(projectList.size(), projectList2.size());
    }

    @Test
    void listAll() {
        Project project1 = Project.builder().id(1L).name("1").build();
        Project project2 = Project.builder().id(2L).name("2").build();
        List<Project> projectList = new ArrayList<>(Arrays.asList(project1, project2));

        when(projectRepository.findAll()).thenReturn(projectList);
        List<Project> projectList2 = projectService.listAll();

        assertEquals(projectList.size(), projectList2.size());
    }

    @Test
    void getById() {
        Project project = Project.builder().id(1L).build();

        when(projectRepository.getById(anyLong())).thenReturn(project);

        Project project1 = projectService.getById(anyLong());
        assertNotNull(project1.getId());
        verify(projectRepository, times(1)).getById(anyLong());
    }

    @Test
    void save() {
        Project project = Project.builder().id(1L).name("Project").build();
        Task task = Task.builder().id(1L).build();

        project.setTasks(new ArrayList<>(Arrays.asList(task)));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project projectSaved= projectService.save(project);

        assertNotNull(projectSaved.getId());
        verify(projectRepository, times(1)).save(any());
    }

    @Test
    void delete() {
        projectService.delete(1L);

        verify(projectRepository, times(1)).deleteById(anyLong());
    }

}