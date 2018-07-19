package br.projectmanagersoftware.entity.project.planning;

import br.projectmanagersoftware.entity.project.AbstractProjectEntity;
import br.projectmanagersoftware.entity.project.Project;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 20/09/2017
 */
@Document
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Risk extends AbstractProjectEntity{

    @Getter
    @Setter
    private String premiseId;
    
    @Getter
    @Setter
    private String description;

    public Risk(String premiseId, String description, Project project) {
        super(project);
        this.premiseId = premiseId;
        this.description = description;
    }

    public Risk(String premiseId, String description) {
        this.premiseId = premiseId;
        this.description = description;
    }

    public Risk(Project project) {
        super(project);
    }

    public Risk() {
    }

}
