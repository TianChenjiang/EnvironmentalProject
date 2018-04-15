package njutj.environment.vo.record;

public class SpeciesCheckVo {
    private long recordId;
    private String name;
    private String lng;
    private String lat;

    public SpeciesCheckVo() {
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public SpeciesCheckVo(long recordId, String name, String lng, String lat) {
        this.recordId = recordId;
        this.name = name;
        this.lng = lng;
        this.lat = lat;

    }
}
