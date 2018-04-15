package njutj.environment.springcontroller.account;

import njutj.environment.blservice.account.UserBlService;
import njutj.environment.exception.viewexception.UserAlreadyExistentException;
import njutj.environment.response.Response;
import njutj.environment.response.SuccessResponse;
import njutj.environment.vo.account.UserLoginVo;
import njutj.environment.vo.account.UserSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserBlService userBlService;

    @Autowired
    public UserController(UserBlService userBlService) {
        this.userBlService = userBlService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "account/login", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Response> login(@RequestBody UserLoginVo userLoginVo) {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, path = "account/register", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Response> register(@RequestBody UserSaveVo userSaveVo) {
        try {
            userBlService.register(userSaveVo);
            return new ResponseEntity<>(new SuccessResponse("save success"), HttpStatus.CREATED);
        } catch (UserAlreadyExistentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/getOpenId", produces = "application/json")
    @ResponseBody
    public String getOpenId(@RequestParam("jsCode") String jsCode) {
        return userBlService.getOpenId(jsCode);
    }
}
