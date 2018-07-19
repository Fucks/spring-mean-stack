package br.projectmanagersoftware.repository.project.info;

import br.projectmanagersoftware.entity.project.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 19/09/2017
 */
public interface IProjectRepository extends MongoRepository<Project, String>{

}
