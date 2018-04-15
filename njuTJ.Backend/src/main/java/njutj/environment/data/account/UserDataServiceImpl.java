package njutj.environment.data.account;

import njutj.environment.data.dao.account.UserDao;
import njutj.environment.dataservice.account.UserDataService;
import njutj.environment.entity.account.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataServiceImpl implements UserDataService {

    private final UserDao userDao;

    @Autowired
    public UserDataServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean isUserExistent(String username) {
        return userDao.findUserByUsername(username) != null;
    }

    /**
     * save user
     *
     * @param user the user to be saved
     */
    @Override
    public void saveUser(User user) {
        userDao.save(user);
    }
}
