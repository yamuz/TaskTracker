package abdrayev.tasktracker.repositories;

import abdrayev.tasktracker.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface ProjectRepositoryCustom {

        List<Project> findProjectsByCustomFields(Map<String, Object> filterFields,  Map<String, Object> sortFields );

}
