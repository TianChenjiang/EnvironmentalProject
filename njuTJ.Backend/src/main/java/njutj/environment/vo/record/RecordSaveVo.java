package njutj.environment.vo.record;

public class RecordSaveVo {
    private String name;
    private String lng;       //经度
    private String lat;       //纬度

    public RecordSaveVo(){
    }

    public RecordSaveVo(String name, String lng, String lat) {
        this.name = name;
        this.lng = lng;
        this.lat = lat;
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

}


