package abdrayev.tasktracker.services;


import abdrayev.tasktracker.domain.Project;

import java.util.List;
import java.util.Map;

public interface ProjectService {
    List<Project> listAll();

    Project getById(Long id);

    Project save(Project project);

    void delete(Long id);

    List<Project> getFilteredProjects(Map<String, Object> filterFields, Map<String, Object> sortFields);
}
