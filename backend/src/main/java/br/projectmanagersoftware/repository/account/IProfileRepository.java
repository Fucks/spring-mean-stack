package br.projectmanagersoftware.repository.account;

import br.projectmanagersoftware.entity.account.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 17/11/2015
 */
public interface IProfileRepository extends MongoRepository<Profile, String> {

    public Profile findByName(String name);
}
