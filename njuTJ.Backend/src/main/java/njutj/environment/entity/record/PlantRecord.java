package njutj.environment.entity.record;

import njutj.environment.publicdatas.record.RecordState;

import javax.persistence.Table;

@Table(name = "plantRecord")
public class PlantRecord extends Record {


    public PlantRecord() {
    }

    public PlantRecord(RecordState recordState) {
        super(recordState);
    }
}
