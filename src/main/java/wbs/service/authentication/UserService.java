package wbs.service.authentication;

import org.springframework.security.core.userdetails.UserDetailsService;
import wbs.model.authentication.User;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
}
