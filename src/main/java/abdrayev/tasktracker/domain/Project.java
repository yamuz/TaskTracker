package abdrayev.tasktracker.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

/**autor -Almaz
 date- 2022-03-27*/
@Entity
@Data
@Table
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date comletionDate;

    @Enumerated(EnumType.ORDINAL)
    private ProjectStatus status;

    @Min(1)
    @Max(10)
    private int priority;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<Task> tasks;
}
