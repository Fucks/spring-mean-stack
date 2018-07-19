package br.projectmanagersoftware.entity.project.planning;

import br.projectmanagersoftware.entity.project.AbstractProjectEntity;
import br.projectmanagersoftware.entity.project.Project;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.TextIndexed;
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
public class Requirement extends AbstractProjectEntity{

    @Getter
    @Setter
    @Field
    private String code;
    
    @Getter
    @Setter
    private String description;
    
    public Requirement() {
        super();
    }

}
