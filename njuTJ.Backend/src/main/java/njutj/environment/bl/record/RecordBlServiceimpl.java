package njutj.environment.bl.record;

import njutj.environment.blservice.record.RecordBlService;
import njutj.environment.dataservice.record.AliCheckDataService;
import njutj.environment.dataservice.record.RecordDataService;
import njutj.environment.entity.record.PlantRecord;
import njutj.environment.publicdatas.record.RecordState;
import njutj.environment.response.record.RecordCreateResponse;
import njutj.environment.response.record.WaitForCheckItem;
import njutj.environment.response.record.WaitForCheckResponse;
import njutj.environment.vo.record.RecordCreateVo;
import njutj.environment.vo.record.SpeciesCheckVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecordBlServiceImpl implements RecordBlService {
    private final RecordDataService recordDataService;
    private final AliCheckDataService aliCheckDataService;

    @Autowired
    public RecordBlServiceImpl(RecordDataService recordDataService, AliCheckDataService aliCheckDataService) {
        this.recordDataService = recordDataService;
        this.aliCheckDataService = aliCheckDataService;
    }

    /**
     * save expert's check result
     *
     * @param speciesCheckVo
     */
    @Override
    public void check(SpeciesCheckVo speciesCheckVo) {
        PlantRecord plantRecord = recordDataService.getRecordByRecordId(speciesCheckVo.getRecordId());
        plantRecord.setPlantName(speciesCheckVo.getName());
        plantRecord.setRecordState(RecordState.RECORDED);
        recordDataService.saveRecord(plantRecord);
    }

    /**
     * create a record
     *
     * @param recordCreateVo
     */
    @Override
    public RecordCreateResponse createRecord(RecordCreateVo recordCreateVo) {
        PlantRecord plantRecord = recordDataService.getRecordByRecordId(recordCreateVo.getRecordId());
        String name = aliCheckDataService.checkImage(plantRecord.getImageUrl());
        if (name != null && name.length() > 0) {
            plantRecord.setPlantName(name);
            plantRecord.setRecordState(RecordState.RECORDED);
            recordDataService.saveRecord(plantRecord);
            return new RecordCreateResponse(plantRecord.getId(), plantRecord.getRecordState(), plantRecord.getPlantName());
        } else {
            plantRecord.setRecordState(RecordState.EXPERTING);
            recordDataService.saveRecord(plantRecord);
            return new RecordCreateResponse(plantRecord.getId(), plantRecord.getRecordState(), null);
        }
    }

    /**
     * load the data of records to be checked
     *
     * @return the records to be checked
     */
    @Override
    public WaitForCheckResponse loadWaitForCheck() {
        List<PlantRecord> plantRecordList = recordDataService.getRecordsByRecordState(RecordState.EXPERTING);
        ArrayList<WaitForCheckItem> waitForCheckItems = new ArrayList<>();
        for (PlantRecord plantRecord : plantRecordList) {
            waitForCheckItems.add(new WaitForCheckItem(plantRecord.getId(), plantRecord.getImageUrl()));
        }
        return new WaitForCheckResponse(waitForCheckItems);
    }
}