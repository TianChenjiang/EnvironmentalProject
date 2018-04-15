package njutj.environment.entity.record;

import njutj.environment.publicdatas.record.RecordState;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "recordState")
    private RecordState recordState;

    public Record() {
    }

    public Record(RecordState recordState) {
        this.recordState = recordState;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RecordState getRecordState() {
        return recordState;
    }

    public void setRecordState(RecordState recordState) {
        this.recordState = recordState;
    }
}
