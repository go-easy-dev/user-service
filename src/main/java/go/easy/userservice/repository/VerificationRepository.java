package go.easy.userservice.repository;

import go.easy.userservice.dto.verification.VerificationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VerificationRepository extends MongoRepository<VerificationEntity, String> {

    Optional<VerificationEntity> findByUserIdAndVerificationCode(String userId, String code);
}
