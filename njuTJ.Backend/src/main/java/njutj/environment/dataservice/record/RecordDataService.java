package njutj.environment.dataservice.record;

import njutj.environment.entity.record.PlantRecord;
import njutj.environment.publicdatas.record.RecordState;

import java.util.List;

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

    /**
     * get records by their record state
     *
     * @param recordState the state of record
     * @return the list of records
     */
    List<PlantRecord> getRecordsByRecordState(RecordState recordState);
}