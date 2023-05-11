package go.easy.userservice.repository;

import go.easy.userservice.dto.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserProfile, String> {

	@Query
	Optional<UserProfile> findByEmail(String email);
}
