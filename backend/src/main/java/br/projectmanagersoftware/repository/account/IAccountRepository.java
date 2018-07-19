package br.projectmanagersoftware.repository.account;

import br.projectmanagersoftware.entity.account.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 29/10/2015
 */
public interface IAccountRepository extends MongoRepository<User, String> {

    public User findUserByUsernameAndEnabledTrue(String username);

    public User findUserByUsername(String username);

    public User findByUsernameAndEmail(String username, String email);

    /**
     *
     * @param criteria
     * @param pageable
     * @return
     */
    public Page<User> findAllBy(TextCriteria criteria, Pageable pageable);
}
