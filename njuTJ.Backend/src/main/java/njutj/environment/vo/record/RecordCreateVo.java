package njutj.environment.vo.record;

public class RecordCreateVo {
    private long recordId;
    private String name;
    private double lng;
    private double lat;

    public RecordCreateVo() {
    }

    public RecordCreateVo(long recordId, String name, double lng, double lat) {
        this.recordId = recordId;
        this.name = name;
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

}
