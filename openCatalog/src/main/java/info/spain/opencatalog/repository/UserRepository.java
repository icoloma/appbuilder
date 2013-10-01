package info.spain.opencatalog.repository;

import info.spain.opencatalog.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 * @author ehdez
 *
 * Repositorio para User
 * Por seguridad no se expone a través del API
 *
 */
public interface UserRepository extends MongoRepository<User, String> {
	
	public User findByEmail(@Param("email") String username);
	
	@Query( value="{'email':{'$regex':?0, '$options': 'i'}}")
	public Page<User> findByEmailIgnoreCaseLike(String name, Pageable pageable);
	

	

}
