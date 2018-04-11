package njutj.environment.springcontroller.record;

import njutj.environment.blservice.upload.ImageUploadBlService;
import njutj.environment.response.Response;
import njutj.environment.vo.record.RecordCreateVo;
import njutj.environment.vo.record.SpeciesCheckVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RecordController {
    private final ImageUploadBlService imageUploadBlService;

    @Autowired
    public RecordController(ImageUploadBlService imageUploadBlService) {
        this.imageUploadBlService = imageUploadBlService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "record/waitForCheck", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Response> waitForCheck(@RequestBody SpeciesCheckVo speciesCheckVo) {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, path = "record/check", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Response> check(@RequestBody SpeciesCheckVo speciesCheckVo) {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, path = "record/uploadImage", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Response> uploadImage(@RequestParam("foodImage") MultipartFile multipartFile) {
        return new ResponseEntity<>(imageUploadBlService.uploadImage(multipartFile), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, path = "record/createRecord", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Response> createRecord(@RequestBody RecordCreateVo recordCreateVo) {
        return null;
    }
}
