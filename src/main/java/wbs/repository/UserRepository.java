package wbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wbs.model.authentication.User;

/**
 * Created by livia on 05.02.17.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
