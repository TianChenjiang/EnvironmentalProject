package njutj.environment.entity.record;

import njutj.environment.publicdatas.record.RecordState;

@Column
public class PlantRecord {
    private long recordId;
    private RecordState recordState;

    public PlantRecord(){

    }

    public RecordState getRecordState() { return recordState; }

    public void setRecordState(RecordState recordState) { this.recordState = recordState; }

    public PlantRecord(RecordState recordState) { this.recordState = recordState; }
}
