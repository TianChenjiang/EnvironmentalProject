package njutj.environment.blservice.account;

import njutj.environment.exception.viewexception.UserAlreadyExistentException;
import njutj.environment.response.account.ExpertCheckResponse;
import njutj.environment.vo.account.UserSaveVo;

public interface UserBlService {

    void register(UserSaveVo userSaveVo) throws UserAlreadyExistentException;

    ExpertCheckResponse check(String work);
}
