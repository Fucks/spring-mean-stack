package br.projectmanagersoftware.entity.project;

import br.projectmanagersoftware.entity.AbstractEntity;
import br.projectmanagersoftware.entity.account.User;
import br.projectmanagersoftware.entity.project.info.Benefit;
import br.projectmanagersoftware.entity.project.planning.Requirement;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
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
public class Project extends AbstractEntity {

    @Getter
    @Setter
    @Field
    private String name;

    @Getter
    @Setter
    @DBRef
    private User owner;

    @Getter
    @Setter
    @Field
    private List<Requirement> requirements;

    @Getter
    @Setter
    @Field
    private List<Benefit> benefits;

    /**
     *
     */
    public Project() {
    }

}
