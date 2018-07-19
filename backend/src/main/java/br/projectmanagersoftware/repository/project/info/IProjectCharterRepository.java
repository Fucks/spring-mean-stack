package br.projectmanagersoftware.repository.project.info;

import br.projectmanagersoftware.entity.project.info.ProjectCharter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 19/09/2017
 */
@Repository
public interface IProjectCharterRepository extends CrudRepository<ProjectCharter, String>{

}
