package njutj.environment.bl.record;

import njutj.environment.blservice.record.RecordBlService;
import njutj.environment.dataservice.record.RecordDataService;
import njutj.environment.entity.record.PlantRecord;
import njutj.environment.publicdatas.record.RecordState;
import njutj.environment.vo.record.SpeciesCheckVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordBlServiceImpl implements RecordBlService {
    private final RecordDataService recordDataService;

    @Autowired
    public RecordBlServiceImpl(RecordDataService recordDataService) {
        this.recordDataService = recordDataService;
    }

    public void check(SpeciesCheckVo speciesCheckVo) {
        PlantRecord plantRecord = recordDataService.getRecordById(speciesCheckVo.getId());
        PlantRecord.setState(RecordState.RECORDED);
        PlantRecord.setName(name);
        PlantRecord.setLng(lng);
        PlantRecord.setLat(lat);
        recordDataService.saveRecord(plantRecord);
    }
}