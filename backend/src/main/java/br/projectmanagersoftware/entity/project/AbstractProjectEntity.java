package br.projectmanagersoftware.entity.project;

import br.projectmanagersoftware.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 19/09/2017
 */
@ToString(callSuper = true, of = {"id"})
@EqualsAndHashCode(callSuper = true, of = {"id"})
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class AbstractProjectEntity extends AbstractEntity{

    @DBRef
    @Getter
    @Setter
    private Project project;

    public AbstractProjectEntity(Project project) {
        this.project = project;
    }

    public AbstractProjectEntity() {
    }
}
