package study.TestSecuriry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.TestSecuriry.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByUsername(String username);

    UserEntity findByUsername(String username);
}
