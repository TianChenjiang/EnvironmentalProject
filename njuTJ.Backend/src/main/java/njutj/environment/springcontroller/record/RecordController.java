package njutj.environment.springcontroller.record;

import njutj.environment.blservice.record.RecordBlService;
import njutj.environment.blservice.upload.ImageUploadBlService;
import njutj.environment.exception.viewexception.SystemException;
import njutj.environment.response.Response;
import njutj.environment.response.SuccessResponse;
import njutj.environment.vo.record.RecordCreateVo;
import njutj.environment.vo.record.SpeciesCheckVo;
import njutj.environment.vo.record.SpeciesCreateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RecordController {
    private final ImageUploadBlService imageUploadBlService;
    private final RecordBlService recordBlService;

    @Autowired
    public RecordController(ImageUploadBlService imageUploadBlService, RecordBlService recordBlService) {
        this.imageUploadBlService = imageUploadBlService;
        this.recordBlService = recordBlService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "record/waitForCheck", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Response> loadWaitForCheck() {
        return new ResponseEntity<>(recordBlService.loadWaitForCheck(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "record/check", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Response> check(@RequestBody SpeciesCheckVo speciesCheckVo) {
        recordBlService.check(speciesCheckVo);
        return new ResponseEntity<>(new SuccessResponse("success save"), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, path = "record/uploadImage", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Response> uploadImage(@RequestParam("foodImage") MultipartFile multipartFile) {
        try {
            return new ResponseEntity<>(imageUploadBlService.uploadImage(multipartFile), HttpStatus.CREATED);
        } catch (SystemException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "record/createRecord", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Response> createRecord(@RequestBody RecordCreateVo recordCreateVo) {
        try {
            return new ResponseEntity<>(recordBlService.createRecord(recordCreateVo), HttpStatus.CREATED);
        } catch (SystemException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 上传新物种
     *
     * @param speciesCreateVo
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, path = "record/uploadRecord", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Response> createSpecies(@RequestBody SpeciesCreateVo speciesCreateVo) {
        return null;
    }

    /**
     * 得到自己所有的记录
     *
     * @param wechatId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "record/queryRecords", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Response> queryRecords(@RequestParam(value = "wechatId") String wechatId) {
        return null;
    }

    /**
     * 得到详细记录
     *
     * @param recordId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "record/queryDetailInfo", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Response> queryDetailInfo(@RequestParam(value = "recordId") long recordId) {
        return null;
    }

}
