package abdrayev.tasktracker.services;

import abdrayev.tasktracker.domain.Project;
import abdrayev.tasktracker.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    public List<Project> getFilteredProjects(Map<String, Object> filterFields, Map<String, Object> sortFields){

        return projectRepository.findProjectsByCustomFields( filterFields, sortFields);
    }

    @Override
    public List<Project> listAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project getById(Long id) {
        return projectRepository.getById(id);
    }

    @Transactional
    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }
}
