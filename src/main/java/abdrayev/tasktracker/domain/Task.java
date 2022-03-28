package abdrayev.tasktracker.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
/**autor -Almaz
 date- 2022-03-27*/
@Entity
@Table
@Data
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

}
