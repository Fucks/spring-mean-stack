package br.projectmanagersoftware.entity.account;

import br.projectmanagersoftware.entity.AbstractEntity;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 30/10/2015
 */
@Document
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Profile extends AbstractEntity {

    @Getter
    @Setter
    @Field
    @NotNull
    @NotEmpty
    private String name;

    @Getter
    @Setter
    private List<String> permissoes;

    /**
     *
     */
    public Profile() {

    }

    public Profile(String id) {
        super(id);
    }
}
