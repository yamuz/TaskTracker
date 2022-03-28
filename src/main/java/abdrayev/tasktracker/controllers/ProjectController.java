package abdrayev.tasktracker.controllers;

import abdrayev.tasktracker.Utils;
import abdrayev.tasktracker.domain.Project;
import abdrayev.tasktracker.repositories.ProjectRepositoryImpl;
import abdrayev.tasktracker.services.ProjectService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//autor -Almaz
//date- 2022-03-27


@Controller
public class ProjectController {
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects/all")
    public String getAllProjects(Model model, @Param(value = "name") String name,
                                 @Param(value = "startDateFrom") String startDateFrom,
                                 @Param(value = "startDateTo") String startDateTo){

        Map<String, Object> filterFields = fieldsToMap(name,  startDateFrom,  startDateTo);
        Map<String, Object> sortFields   = new HashMap<>(); //fieldsToMap(name,  startDateFrom,  startDateTo);

        List<Project> projectList = null;
        if ( filterFields.isEmpty() && sortFields.isEmpty() )
           projectList = projectService.listAll();
        else
           projectList = projectService.getFilteredProjects(filterFields, sortFields);

        model.addAttribute("projectList", projectList);
        model.addAttribute("name", name);
        model.addAttribute("startDateFrom", startDateFrom );
        model.addAttribute("startDateTo", startDateTo);
        return "/projects/projectList";
    }

    @GetMapping("/projects/edit/{id}")
    public String getProject(@PathVariable Long id, Model model){
        Project project = projectService.getById(id);
        model.addAttribute("project", project);

        return "/projects/project";
    }

    @PostMapping("/projects/edit/")
    public String saveProject( Project project){
        Project savedProject = projectService.save(project);
        return "redirect:/projects/edit/" + savedProject.getId().toString();
    }

    private Map<String, Object> fieldsToMap(String name, String startDateFrom, String startDateTo){
        Map<String, Object> map = new HashMap<>();
        if ( name!=null && !name.isBlank())
            map.put(ProjectRepositoryImpl.NAME, name);
        if( startDateFrom!=null && !startDateFrom.isBlank())
            map.put(ProjectRepositoryImpl.START_DATE_FROM, startDateFrom);
        if( startDateTo!=null && !startDateTo.isBlank())
            map.put(ProjectRepositoryImpl.START_DATE_TO, startDateTo);
        return map;
    }
}
