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
    @Column(name = "lng")
    private double lng;
    @Column(name = "lat")
    private double lat;

    public Record() {
    }

    public Record(RecordState recordState, String imageUrl, double lng, double lat) {
        this.recordState = recordState;
        this.imageUrl = imageUrl;
        this.lng = lng;
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
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
