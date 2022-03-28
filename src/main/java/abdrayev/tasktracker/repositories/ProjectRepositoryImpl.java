package abdrayev.tasktracker.repositories;

import abdrayev.tasktracker.Utils;
import abdrayev.tasktracker.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**autor -Almaz
date- 2022-03-28
Custom repository implementaion with filtering*/
public class ProjectRepositoryImpl implements ProjectRepositoryCustom{
    public static final String START_DATE_FROM = "startDateFrom";
    public static final String START_DATE_TO   = "startDateTo";
    public static final String NAME            = "name";

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Project> findProjectsByCustomFields(Map<String, Object> filterFields, Map<String, Object> sortFields) {
        Query queryTotal =  buildQuery(filterFields, sortFields);
        return queryTotal.getResultList();
    }

    /**Building query using entityManager and getCriteriaBuilder
    filtering of projects works (by 29.03.2022) with name field,
    also filtering works in period (between startDate and comletionDate)*/
    private Query buildQuery(Map<String, Object> filterFields, Map<String, Object> sortFields){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> query = cb.createQuery(Project.class);
        Root<Project> projectRoot = query.from(Project.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filterFields.containsKey(NAME) && filterFields.get(NAME)!=""){
            predicates.add(cb.like(projectRoot.get("name"), "%" + filterFields.get(NAME)+ "%"));
        }
        if (filterFields.containsKey(START_DATE_FROM)&& filterFields.get(START_DATE_FROM)!=""){
            predicates.add(cb.greaterThanOrEqualTo(projectRoot.get("startDate"),
                    Utils.fromStringToDate((String) filterFields.get(START_DATE_FROM))));
        }
        if (filterFields.containsKey(START_DATE_TO) && filterFields.get(START_DATE_TO)!=""){
            predicates.add(cb.lessThanOrEqualTo(projectRoot.get("comletionDate"),
                    Utils.fromStringToDate((String) filterFields.get(START_DATE_TO))));
        }

        query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query);
    }

}
