package njutj.environment.springcontroller.chat;

import njutj.environment.response.Response;
import njutj.environment.vo.chat.ChatCreateVo;
import njutj.environment.vo.chat.CommentCreateVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public class ChatController {
    /**
     * 发表信息
     *
     * @param chatCreateVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "record/queryDetailInfo", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Response> queryDetailInfo(@RequestBody ChatCreateVo chatCreateVo) {
        return null;
    }

    /**
     * 发表评论
     *
     * @param commentCreateVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "record/queryDetailInfo", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Response> queryDetailInfo(@RequestBody CommentCreateVo commentCreateVo) {
        return null;
    }
}
