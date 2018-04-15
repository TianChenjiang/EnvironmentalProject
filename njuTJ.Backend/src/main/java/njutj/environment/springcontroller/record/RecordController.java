package njutj.environment.springcontroller.record;

import njutj.environment.blservice.record.RecordBlService;
import njutj.environment.response.Response;
import njutj.environment.response.record.RecordSaveResponse;
import njutj.environment.vo.record.SpeciesCheckVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecordController {
    private final RecordBlService recordBlService;

    @Autowired
    public RecordController(RecordBlService recordBlService) {
        this.recordBlService = recordBlService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "record/waitForCheck", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Response> waitForCheck(@RequestBody SpeciesCheckVo speciesCheckVo) {
        return null;
    }


    @RequestMapping(method = RequestMethod.POST, path = "record/check", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Response> check(@RequestBody SpeciesCheckVo speciesCheckVo) {

        recordBlService.check(speciesCheckVo);
        recordBlService.saveRecord(SpeciesCheckVo);
        return new ResponseEntity<>(new RecordSaveResponse("save success"), HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.POST, path = "record/check", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Response> check(@RequestBody SpeciesCheckVo speciesCheckVo) {
        return null;
    }
}
