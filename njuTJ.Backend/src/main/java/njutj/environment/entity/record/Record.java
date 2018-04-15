package njutj.environment.entity.record;

import njutj.environment.publicdatas.record.RecordState;

import javax.persistence.*;

@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "recordState")
    private RecordState recordState;
    @Column(name = "image")
    private String imageUrl;

    public Record() {
    }

    public Record(RecordState recordState, String imageUrl) {
        this.recordState = recordState;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
