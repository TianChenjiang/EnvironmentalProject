package njutj.environment.dataservice.account;

import njutj.environment.entity.account.User;

public interface UserDataService {

    boolean isUserExistent(String username);

    /**
     * save user
     *
     * @param user the user to be saved
     */
    void saveUser(User user);
}
