package mvc.model.project;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@EqualsAndHashCode
@Setter
@Getter
public class ProjectSpecialFormat {
    private LocalDate creationDate;
    private String name;
    private int developerCount;

    @Override
    public String toString() {
        return "Project{" +
                "creationDate=" + creationDate +
                ", name='" + name + '\'' +
                ", developerCount=" + developerCount +
                '}';
    }
}