package njutj.environment.bl.account;

import njutj.environment.blservice.account.UserBlService;
import njutj.environment.dataservice.account.UserDataService;
import njutj.environment.exception.viewexception.UserAlreadyExistentException;
import njutj.environment.vo.account.UserSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserBlServiceImpl implements UserBlService {
    @Value(value = "${wechat.url}")
    private String wechatUrl;

    @Value(value = "${wechat.id}")
    private String appId;

    @Value(value = "${wechat.secret}")
    private String appSecret;

    private final UserDataService userDataService;

    @Autowired
    public UserBlServiceImpl(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @Override
    public void register(UserSaveVo userSaveVo) throws UserAlreadyExistentException {
        if (userDataService.isUserExistent(userSaveVo.getUsername())) {
            throw new UserAlreadyExistentException();
        }
    }

    /**
     * get user openid
     *
     * @param jsCode wechat js code
     * @return whether the operation is success or not
     */
    @Override
    public String getOpenId(String jsCode) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>("", headers);
        ResponseEntity<String> response = client.exchange(wechatUrl + appId + "&secret=" + appSecret + "&js_code=" + jsCode + "&grant_type=authorization_code", HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return "";
        }
    }
}
