package br.projectmanagersoftware.entity.project.planning;

import br.projectmanagersoftware.entity.project.AbstractProjectEntity;
import br.projectmanagersoftware.entity.project.Project;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 19/09/2017
 */
@Document
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectTask extends AbstractProjectEntity {

    @Getter
    @Setter
    @Field
    private String name;

    @Getter
    @Setter
    @Field
    private DateTime startAt;

    @Getter
    @Setter
    @Field
    private DateTime endAt;

    @Getter
    @Setter
    @Field
    private List<ProjectTask> childs;

    @Getter
    @Setter
    @DBRef
    @JsonBackReference
    private ProjectTask parent;

    @Getter
    @Setter
    private Integer depth;

    @Getter
    @Setter
    private ProjectTaskType type;
    
    @Getter
    @Setter
    private Integer duration;
    
    @Getter
    @Setter
    private Integer index;
    
    @Getter
    @Setter
    private DurationUnitMeasure unitMeasureDuration;

    public ProjectTask() {
        super();
    }

    public ProjectTask(String name, DateTime startAt, DateTime endAt, List<ProjectTask> childs, ProjectTask parent, Integer depth, ProjectTaskType type, Integer duration, Integer index, DurationUnitMeasure unitMeasureDuration, Project project) {
        super(project);
        this.name = name;
        this.startAt = startAt;
        this.endAt = endAt;
        this.childs = childs;
        this.parent = parent;
        this.depth = depth;
        this.type = type;
        this.duration = duration;
        this.index = index;
        this.unitMeasureDuration = unitMeasureDuration;
    }

    public ProjectTask(String name, DateTime startAt, DateTime endAt, List<ProjectTask> childs, ProjectTask parent, Integer depth, ProjectTaskType type, Integer duration, Integer index, DurationUnitMeasure unitMeasureDuration) {
        this.name = name;
        this.startAt = startAt;
        this.endAt = endAt;
        this.childs = childs;
        this.parent = parent;
        this.depth = depth;
        this.type = type;
        this.duration = duration;
        this.index = index;
        this.unitMeasureDuration = unitMeasureDuration;
    }
    

    
}
