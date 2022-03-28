package abdrayev.tasktracker.controllers;

import abdrayev.tasktracker.domain.Project;
import abdrayev.tasktracker.domain.Task;
import abdrayev.tasktracker.services.ProjectService;
import abdrayev.tasktracker.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TaskController {
    private TaskService taskService;
    private ProjectService projectService;

    public TaskController(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @GetMapping("/projects/edit/{projectId}/tasks/edit/{id}")
    public String getTask(Model model, @PathVariable Long id, @PathVariable Long projectId){
        Task task = taskService.getById(id);
        model.addAttribute("task", task);
        return "tasks/task";
    }

    @GetMapping("/projects/edit/{projectid}/tasks/new/")
    public String newTask(@PathVariable Long projectid, Model model){

        Project project = projectService.getById(projectid);
        Task task = new Task();
        task.setProject(project);
        model.addAttribute("task", task);
        return "tasks/task";
    }

    @GetMapping("/projects/edit/{projectId}/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long projectId, @PathVariable Long id){
        taskService.delete(id);
        return "redirect:/projects/edit/{projectId}";
    }

    @PostMapping("/projects/edit/{projectId}/tasks/edit/{id}")
    public String saveTask(@PathVariable Long projectId, Task task){
        task.setProject(projectService.getById(projectId));
        Task savedTask = taskService.save(task);
        return "redirect:/projects/edit/{projectId}";
    }

    @PostMapping("/projects/edit/{projectId}/tasks/new/")
    public String newTask(@PathVariable Long projectId, Task task ){
        Project project = projectService.getById(projectId);
        task.setProject(project);
        Task savedTask = taskService.save(task);
        return "redirect:/projects/edit/{projectId}";
    }

}
