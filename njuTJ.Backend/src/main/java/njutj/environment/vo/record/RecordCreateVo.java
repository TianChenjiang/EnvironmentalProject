package njutj.environment.vo.record;

public class RecordCreateVo {
    private String imageUrl;
    private String position;

    public RecordCreateVo() {
    }

    public RecordCreateVo(String imageUrl, String position) {
        this.imageUrl = imageUrl;
        this.position = position;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
