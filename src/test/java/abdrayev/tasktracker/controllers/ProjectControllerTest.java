package abdrayev.tasktracker.controllers;

import abdrayev.tasktracker.domain.Project;
import abdrayev.tasktracker.services.ProjectService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
class ProjectControllerTest {

    @Mock
    ProjectService projectService;

    ProjectController projectController;

    MockMvc mockMvc;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        projectController = new ProjectController(projectService);
        mockMvc = MockMvcBuilders.standaloneSetup(projectController)
                .setControllerAdvice().build();
    }
    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    void getAllProjects() throws Exception {
        List<Project> projectList = new ArrayList<>();
        when(projectService.listAll()).thenReturn(projectList);

        mockMvc.perform(get("/projects/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("/projects/projectList"))
                .andExpect(model().attributeExists("projectList"));
    }

    @Test
    void getProject() throws Exception {
        Project project = Project.builder().id(1L).build();

        when(projectService.getById(anyLong())).thenReturn(project);

        mockMvc.perform(get("/projects/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/projects/project"))
                .andExpect(model().attributeExists("project"));
    }

    @Test
    void saveProject() throws Exception {
        Project project = Project.builder().id(1L).build();

        when(projectService.save(any(Project.class))).thenReturn(project);
        mockMvc.perform(post("/projects/edit/")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/projects/edit/1"));

    }
}