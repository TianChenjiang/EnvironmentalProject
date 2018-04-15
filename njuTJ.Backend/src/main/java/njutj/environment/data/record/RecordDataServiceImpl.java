package njutj.environment.data.record;

import njutj.environment.data.dao.record.PlantRecordDao;
import njutj.environment.dataservice.record.RecordDataService;
import njutj.environment.entity.record.PlantRecord;
import org.springframework.beans.factory.annotation.Autowired;

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
}