package abdrayev.tasktracker.repositories;

import abdrayev.tasktracker.domain.Project;

import java.util.List;
import java.util.Map;
/**autor -Almaz
date- 2022-03-28
CUstom repository interface with filtering*/
public interface ProjectRepositoryCustom {
   List<Project> findProjectsByCustomFields(Map<String, Object> filterFields,  Map<String, Object> sortFields );
}
