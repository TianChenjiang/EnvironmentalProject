package njutj.environment.entity.record;

import njutj.environment.publicdatas.record.RecordState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "plantRecord")
public class PlantRecord extends Record {

    @Column(name = "plantName")
    private String plantName;

    public PlantRecord() {
    }

    public PlantRecord(String plantName) {
        this.plantName = plantName;
    }

    public PlantRecord(RecordState recordState, String imageUrl, double lng, double lat, String plantName) {
        super(recordState, imageUrl, lng, lat);
        this.plantName = plantName;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }
}
