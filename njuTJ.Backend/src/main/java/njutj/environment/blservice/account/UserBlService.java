package njutj.environment.blservice.account;

import njutj.environment.exception.viewexception.UserAlreadyExistentException;
import njutj.environment.vo.account.UserSaveVo;

public interface UserBlService {

    void register(UserSaveVo userSaveVo) throws UserAlreadyExistentException;

    /**
     * get user openid
     *
     * @param jsCode wechat js code
     * @return whether the operation is success or not
     */
    String getOpenId(String jsCode);
}
