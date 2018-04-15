package njutj.environment.dataservice.record;

import njutj.environment.entity.record.PlantRecord;

public interface RecordDataService {
    /**
     * save record
     *
     * @param plantRecord
     * @return the plant record object
     */
    PlantRecord saveRecord(PlantRecord plantRecord);

    /**
     * get record object by its id
     *
     * @param recordId
     * @return the plant record object
     */
    PlantRecord getRecordByRecordId(long recordId);
}