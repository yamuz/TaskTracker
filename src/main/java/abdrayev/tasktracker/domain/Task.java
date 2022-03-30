package abdrayev.tasktracker.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
/**autor -Almaz
 date- 2022-03-27*/
@Entity
@Table
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @Min(1)
    @Max(10)
    private int priority;

    @Enumerated(EnumType.ORDINAL)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Task() {
    }
    @Builder
    public Task(Long id, String name, String description, int priority, TaskStatus status, Project project) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.project = project;
    }
}
