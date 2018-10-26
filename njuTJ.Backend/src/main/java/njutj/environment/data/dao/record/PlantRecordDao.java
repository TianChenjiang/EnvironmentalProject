package njutj.environment.data.dao.record;

import njutj.environment.entity.record.PlantRecord;
import njutj.environment.publicdatas.record.RecordState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRecordDao extends JpaRepository<PlantRecord, Integer> {
    List<PlantRecord> findPlantRecordsByRecordState(RecordState recordState);

    PlantRecord findPlantRecordById(long id);
}
