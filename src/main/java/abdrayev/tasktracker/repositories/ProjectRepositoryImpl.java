package abdrayev.tasktracker.repositories;

import abdrayev.tasktracker.Utils;
import abdrayev.tasktracker.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.nio.channels.FileLock;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class ProjectRepositoryImpl implements ProjectRepositoryCustom{
    public static final String START_DATE_FROM = "startDateFrom";
    public static final String START_DATE_TO   = "startDateTo";
    public static final String END_DATE_FROM   = "endDateFrom";
    public static final String END_DATE_TO     = "endDateTo";
    public static final String NAME            = "name";

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Project> findProjectsByCustomFields(Map<String, Object> filterFields, Map<String, Object> sortFields) {
        Query queryTotal =  buildQuery(filterFields, sortFields);
        return queryTotal.getResultList();
    }

    private Query buildQuery(Map<String, Object> filterFields, Map<String, Object> sortFields){
        /*  Query query = null;
        String queryString = "SELECT from Project where ";
        query = entityManager.createQuery(queryString);
        if (filterFields.containsKey(START_DATE_FROM)){
            queryString = addSubstring(queryString, " AND ");
            query.setParameter(START_DATE_FROM, (Date) filterFields.get(START_DATE_FROM));
        }
        if (filterFields.containsKey(START_DATE_TO)){
            queryString = addSubstring(queryString, " AND ");
            query.setParameter(START_DATE_TO, (Date) filterFields.get(START_DATE_TO));
        }*/


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

        //Predicate finalPredicate  = cb.and(predicateForColor, predicateForGrade);
        query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query);
    }


    private String addSubstring(String queryString,  String subString){
        if (queryString.contains(" AND "))
            queryString += subString;

        return queryString;
    }
}
