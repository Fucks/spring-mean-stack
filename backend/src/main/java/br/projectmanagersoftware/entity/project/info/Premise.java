package br.projectmanagersoftware.entity.project.info;

import br.projectmanagersoftware.entity.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
public class Premise extends AbstractEntity{

    @Getter
    @Setter
    @Field
    private String description;

    public Premise() {
    }
    
}
