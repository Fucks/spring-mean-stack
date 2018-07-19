package br.projectmanagersoftware.entity;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 29/10/2015
 */
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public abstract class AbstractEntity implements IEntity<String> {

    @Setter
    @Getter
    @Version
    protected Long version;
    
    @Setter
    @Id
    @Getter
    protected String id;

    @Setter
    @Getter
    @CreatedDate
    protected LocalDateTime createdAt;

    @Setter
    @Getter
    @LastModifiedDate
    protected LocalDateTime updatedAt;

    @Setter
    @Getter
    @CreatedBy
    protected String createdBy;

    @Setter
    @Getter
    @LastModifiedBy
    protected String updatedBy;

    /*-------------------------------------------------------------------
     * 		 					CONSTRUCTORS
     *-------------------------------------------------------------------*/
    /**
     *
     */
    public AbstractEntity() {
    }

    /**
     *
     * @param id
     */
    public AbstractEntity(String id) {
        this.id = id;
    }
}
