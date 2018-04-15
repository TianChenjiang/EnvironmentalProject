package njutj.environment.data.record;

import njutj.environment.data.dao.record.PlantRecordDao;
import njutj.environment.dataservice.record.RecordDataService;
import njutj.environment.entity.record.PlantRecord;
import njutj.environment.publicdatas.record.RecordState;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RecordDataServiceImpl implements RecordDataService {
    private final PlantRecordDao plantRecordDao;

    @Autowired
    public RecordDataServiceImpl(PlantRecordDao plantRecordDao) {
        this.plantRecordDao = plantRecordDao;
    }

    /**
     * save record
     *
     * @param plantRecord
     * @return the plant record object
     */
    @Override
    public PlantRecord saveRecord(PlantRecord plantRecord) {
        return plantRecordDao.save(plantRecord);
    }

    /**
     * get record object by its id
     *
     * @param recordId
     * @return the plant record object
     */
    @Override
    public PlantRecord getRecordByRecordId(long recordId) {
        return plantRecordDao.findPlantRecordById(recordId);
    }

    /**
     * get records by their record state
     *
     * @param recordState the state of record
     * @return the list of records
     */
    @Override
    public List<PlantRecord> getRecordsByRecordState(RecordState recordState) {
        return plantRecordDao.findPlantRecordsByRecordState(recordState);
    }
}