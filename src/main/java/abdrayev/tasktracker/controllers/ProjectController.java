package abdrayev.tasktracker.controllers;

import abdrayev.tasktracker.domain.Project;
import abdrayev.tasktracker.repositories.ProjectRepositoryImpl;
import abdrayev.tasktracker.services.ProjectService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**autor -Almaz
 date- 2022-03-27*/

@Controller
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects/all")
    public String getAllProjects(Model model, @Param(value = "name") String name,
                                 @Param(value = "startDateFrom") String startDateFrom,
                                 @Param(value = "startDateTo") String startDateTo,
                                 @Param(value="comletionDateFrom") String comletionDateFrom,
                                 @Param(value="comletionDateTo") String comletionDateTo){

        Map<String, Object> filterFields = fieldsToMap(name,  startDateFrom,  startDateTo,
                                           comletionDateFrom, comletionDateTo);
        //TO-DO sorting not done yet
        Map<String, Object> sortFields   = new HashMap<>();

        List<Project> projectList;
        if ( filterFields.isEmpty() && sortFields.isEmpty() )
           projectList = projectService.listAll();
        else
           projectList = projectService.getFilteredProjects(filterFields, sortFields);

        model.addAttribute("projectList", projectList);
        model.addAttribute("name", name);
        model.addAttribute("startDateFrom", startDateFrom );
        model.addAttribute("startDateTo", startDateTo);
        model.addAttribute("comletionDateFrom", comletionDateFrom);
        model.addAttribute("comletionDateTo", comletionDateTo);
        return "/projects/projectList";
    }

    @GetMapping("/projects/edit/{id}")
    public String getProject(@PathVariable Long id, Model model){
        Project project = projectService.getById(id);
        model.addAttribute("project", project);

        return "/projects/project";
    }

    @GetMapping("/projects/new/")
    public String newProject(Model model){
        Project project = new Project();
        model.addAttribute("project", project);
        return "/projects/project";
    }

    @GetMapping("/projects/delete/{id}")
    public String deleteProject(@PathVariable Long id){
        projectService.delete(id);
        return "redirect:/projects/all/";
    }

    @PostMapping("/projects/edit/")
    public String saveProject( Project project){
        Project savedProject = projectService.save(project);
        return "redirect:/projects/edit/" + savedProject.getId().toString();
    }

    private Map<String, Object> fieldsToMap(String name, String startDateFrom, String startDateTo,
                                           String comletionDateFrom , String comletionDateTo){
        Map<String, Object> map = new HashMap<>();
        if ( name!=null && !name.isEmpty())
            map.put(ProjectRepositoryImpl.NAME, name);
        if( startDateFrom!=null && !startDateFrom.isEmpty())
            map.put(ProjectRepositoryImpl.START_DATE_FROM, startDateFrom);
        if( startDateTo!=null && !startDateTo.isEmpty())
            map.put(ProjectRepositoryImpl.START_DATE_TO, startDateTo);
        if( comletionDateFrom!=null && !comletionDateFrom.isEmpty())
            map.put(ProjectRepositoryImpl.COMPLETION_DATE_FROM, comletionDateFrom);
        if( comletionDateTo!=null && !comletionDateTo.isEmpty())
            map.put(ProjectRepositoryImpl.COMPLETION_DATE_TO, comletionDateTo);
        return map;
    }
}
