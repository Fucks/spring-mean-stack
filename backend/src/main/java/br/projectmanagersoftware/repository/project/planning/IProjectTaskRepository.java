package br.projectmanagersoftware.repository.project.planning;

import br.projectmanagersoftware.entity.project.planning.ProjectTask;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 19/09/2017
 */
public interface IProjectTaskRepository extends CrudRepository<ProjectTask, String> {

}
