package njutj.environment.data.dao.account;

import njutj.environment.entity.account.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);
}
