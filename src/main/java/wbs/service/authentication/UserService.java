package wbs.service.authentication;

import org.springframework.security.core.userdetails.UserDetailsService;
import wbs.model.authentication.User;

/**
 * Created by livia on 05.02.17.
 */
public interface UserService extends UserDetailsService {
    User findByUsername(String username);
}
