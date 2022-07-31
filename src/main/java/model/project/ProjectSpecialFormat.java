package model.project;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@EqualsAndHashCode
@Setter
@Getter
public class ProjectSpecialFormat {
    private LocalDate creationDate;
    private String name;
    private int developerCount;
}